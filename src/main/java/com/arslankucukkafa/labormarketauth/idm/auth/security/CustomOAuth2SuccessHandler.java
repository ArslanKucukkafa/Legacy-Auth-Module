package com.arslankucukkafa.labormarketauth.idm.auth.security;

import com.arslankucukkafa.labormarketauth.idm.auth.model.JwtDto;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.Syncer;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.SyncerFactory;
import com.arslankucukkafa.labormarketauth.idm.user.repository.UserRepository;
import com.arslankucukkafa.labormarketauth.util.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.token.validity}")
    private long tokenValidity;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomOAuth2SuccessHandler.class);
    private final UserRepository userRepository;
    private final SyncerFactory syncerFactory;
    private final JwtService jwtService;

    public CustomOAuth2SuccessHandler(UserRepository userRepository, SyncerFactory syncerFactory, JwtService jwtService) {
        this.userRepository = userRepository;
        this.syncerFactory = syncerFactory;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        // Authentication içerisinde kullanıcı bilgilerini alabilirsin
         OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        var provider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        Syncer syncer = syncerFactory.getSyncer(provider);

         UserModel mappedUser = syncer.sync(oAuth2User);
         LOGGER.trace("User is mapped: {}", mappedUser);

         // Kullanıcıyı veritabanında ara
         UserModel currentUser = userRepository.findUserModelByContactEmail(mappedUser.getContact().getEmail()).orElse(null);

         // FIXME: Burda kullanıcı zaten güncellse update etmeme gerek yok ? check this condition
         if (currentUser != null) {
            currentUser = syncer.updateModel(currentUser, mappedUser);
            userRepository.save(currentUser);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LOGGER.trace("User is updated: {}", currentUser);

         }
        else {
            userRepository.save(mappedUser);
             UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(mappedUser, null, mappedUser.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LOGGER.trace("User is saved: {}", mappedUser);
         }
        // token oluştur ve kullanıcıya geri dön
        var authToken = jwtService.generateToken(mappedUser.getContact().getEmail(), secret, tokenValidity);
        JwtDto jwtDto = new JwtDto(authToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), jwtDto);
    }
}
