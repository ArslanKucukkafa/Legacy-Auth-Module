package com.arslankucukkafa.labormarketauth.idm.auth.security;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.user.service.UserService;
import com.arslankucukkafa.labormarketauth.util.JwtService;
import com.arslankucukkafa.labormarketauth.util.PrincipalHolder;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
// ctienzship_todo: refactor this class name because jwt filter is general name
public class JwtFilter extends OncePerRequestFilter  {

    @Value("${app.jwt.secret}")
    private String secret;
    private final JwtService jwtService;

    private final UserService userService;

    public JwtFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;


        if(requestTokenHeader!=null&& requestTokenHeader.startsWith("Bearer")){
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtService.getSubjectFromToken(secret,jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token!");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired!");
            }
        }else{
            logger.warn("Token Must be start With Bearer. JWT Token does not begin with Bearer String");
        }

        if (email != null && jwtToken!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Fixme: AYNI email ile birden fazla kullanıcı oldugunda hata alınabilir. init.json dosyasında email alanını unique yapabilirsiniz.
            UserModel userDetails = userService.loadUserByUsername(email);
            if (jwtService.validateToken(secret,jwtToken)) {
                System.out.println(jwtToken);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        else{
            System.out.println("else working security");
        }
        filterChain.doFilter(request, response);
    }
}
