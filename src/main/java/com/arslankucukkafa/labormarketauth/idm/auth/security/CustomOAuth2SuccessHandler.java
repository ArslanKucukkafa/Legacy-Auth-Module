package com.arslankucukkafa.labormarketauth.idm.auth.security;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.adress.repository.AddressRepository;
import com.arslankucukkafa.labormarketauth.idm.adress.service.AddressService;
import com.arslankucukkafa.labormarketauth.idm.auth.model.JwtDto;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.Provider;
import com.arslankucukkafa.labormarketauth.idm.auth.service.AuthService;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.contact.repository.ContactRepository;
import com.arslankucukkafa.labormarketauth.idm.contact.service.ContactService;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.Syncer;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.SyncerFactory;
import com.arslankucukkafa.labormarketauth.idm.user.repository.UserRepository;
import com.arslankucukkafa.labormarketauth.idm.user.service.UserService;
import com.arslankucukkafa.labormarketauth.util.JwtService;
import com.arslankucukkafa.labormarketauth.util.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserService userService;
    private final ContactService contactService;
    private final AddressService addressService;
    private final SyncerFactory syncerFactory;
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public CustomOAuth2SuccessHandler(SyncerFactory syncerFactory, JwtService jwtService,
                                      AuthService authService, UserService userService,
                                      ContactService contactService, AddressService addressService) {

        this.syncerFactory = syncerFactory;
        this.jwtService = jwtService;
        this.authService = authService;
        this.userService = userService;
        this.contactService = contactService;
        this.addressService = addressService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        var provider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        Syncer syncer = syncerFactory.getSyncer(provider);

        ContactModel mappedContact = syncer.contactSync(oAuth2User);
        AddressModel mappedAddress = syncer.addressSync(oAuth2User);
        UserModel mappedUser = syncer.userSync(oAuth2User);

        // checker for user: it is checks contact model, username like unique fields
        var existsUser = authService.checkIfUserExists(mappedContact,mappedUser);
        if (existsUser.isPresent()) {
            // db'de böyle bir user varmış.
            UserModel userDetails = existsUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));
            /**
            * @param isUpdateNeeded : Neden bu kontrol yapılıyor?
            *              1- Kullanıcı daha önce bu provider ile login olmuşsa, Herseferinde update işlemi olmamalı. sadece token dönmeli
            *              2- Kullanıcı daha önce bu provider ile login olmamışsa, providerdan gelen bilgiler ile db güncellenmeli.
            */
             boolean isUpdateNeeded = userDetails.getProvider().contains(Provider.valueOf(provider));
            if (isUpdateNeeded) {
                updateModel(mappedUser, mappedContact, mappedAddress, syncer);
            }
            setAuthentication(userDetails);
            LOGGER.trace("User is updated: {}", userDetails);
        } else {
            var createdUser = authService.createUser(mappedContact, mappedAddress, mappedUser);
            setAuthentication(createdUser);
            LOGGER.trace("User is saved: {}", createdUser);
        }

        var authToken = jwtService.generateToken(mappedUser.getContact().getEmail(), secret, tokenValidity);
        JwtDto jwtDto = new JwtDto(authToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), jwtDto);
    }

    private void updateModel (UserModel userModel, ContactModel contactModel, AddressModel addressModel, Syncer sync) {
        var currentUser = userService.findUserModel(userModel).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var updatedUser = sync.updateUserModel(currentUser, userModel);

        var currentContact = contactService.getContactWithProperties(contactModel).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        var updatedContact = sync.updateContactModel(currentContact, contactModel);

        var currentAddress = addressService.getAddressWithId(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        var updatedAddress = sync.updateAddressModel(currentAddress, addressModel);

        authService.createUser(updatedContact, updatedAddress, updatedUser);
    }

    private void setAuthentication(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * TODO: Burda ilgili user için, email adresine bir login yada signup bilgisi yollamalı mıyız?
     * @param userDetails
     */
}
