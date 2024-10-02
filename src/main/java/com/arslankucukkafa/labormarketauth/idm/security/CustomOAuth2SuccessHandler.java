package com.arslankucukkafa.labormarketauth.idm.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        // Authentication içerisinde kullanıcı bilgilerini alabilirsin
         OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Eğer kullanıcı Google ile giriş yapıyorsa (Google OIDC kullanıyor)
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OIDC_USER"))) {
            // Google OIDC kullanıcısı için yapılacak işlemler
            handleGoogleLogin(oAuth2User);
        }
        // Eğer kullanıcı GitHub ile giriş yapıyorsa (OAuth2 kullanıyor)
        else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OAUTH2_USER"))) {
            // GitHub OAuth2 kullanıcısı için yapılacak işlemler
            handleGitHubLogin(oAuth2User);
        }

        // Başarılı girişten sonra kullanıcıyı yönlendireceğin yer
        response.sendRedirect("/home");
    }

    private void handleGoogleLogin(OAuth2User oAuth2User) {
        // Google kullanıcı bilgilerini işleme
        String email = oAuth2User.getAttribute("email");
        System.out.println("Google ile giriş yapan kullanıcı: " + email);
        // Veritabanına kaydetme, güncelleme, vb. işlemler
    }

    private void handleGitHubLogin(OAuth2User oAuth2User) {
        // GitHub kullanıcı bilgilerini işleme
        String username = oAuth2User.getAttribute("login");
        System.out.println("GitHub ile giriş yapan kullanıcı: " + username);
        // Veritabanına kaydetme, güncelleme, vb. işlemler
    }
}
