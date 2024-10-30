package com.arslankucukkafa.labormarketauth.idm.auth.security;

import com.arslankucukkafa.labormarketauth.idm.auth.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableAsync
@EnableWebSecurity
public class SecurityConfiguation implements WebMvcConfigurer {
    private final PermissionInterceptor permissionInterceptor;
    private final JwtFilter jwtFilter;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    public SecurityConfiguation(PermissionInterceptor permissionInterceptor, JwtFilter jwtFilter, CustomOAuth2SuccessHandler customOAuth2SuccessHandler) {
        this.permissionInterceptor = permissionInterceptor;
        this.jwtFilter = jwtFilter;
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor);
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http = http.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/login", "/oauth2/authorization/**", "/api/v1/auth/hello", "/api/v1/auth/signup","/api/v1/citizenship/turkey/**","/api/v1/**").permitAll()  // login sayfasına ve OAuth URL'lerine izin ver
                        .anyRequest().authenticated()).oauth2Login(
                                oauth2 ->oauth2.clientRegistrationRepository(clientRegistrationRepository())
                                .successHandler(customOAuth2SuccessHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // JWT filtre ekleniyor
        return http.build();
    }


    // Burda kullanılacak OAuth2 servislerini tanımlıyoruz
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("Ov23liprguqYRDV72SXS").clientSecret("be44ed2294203bfe80d1b97a8e9e4961972fc230").build());
        registrations.add(CommonOAuth2Provider.GOOGLE.getBuilder("google").clientId("171269310913-p870ahuhjim9ea7ltes1gk4u52nr0r66.apps.googleusercontent.com").clientSecret("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").build());
        return new InMemoryClientRegistrationRepository(registrations);
    }
}