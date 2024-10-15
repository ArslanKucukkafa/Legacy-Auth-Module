package com.arslankucukkafa.labormarketauth.util.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.jwt.email")
@Component
public class EmailAuthConfigurationProperties {
    private Register register;

    private Login login;


    // arslan.kucukkafa: regster propertiesleri kayıt aşamasında kullanılacak
    public static class Register {
        private String secret;
        private long validity;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getValidity() {
            return validity;
        }

        public void setValidity(long validity) {
            this.validity = validity;
        }
    }

    // arslan.kucukkafa: login propertiesleri giriş aşamasında kullanılacak
    public static class Login {
        private String secret;
        private long validity;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getValidity() {
            return validity;
        }

        public void setValidity(long validity) {
            this.validity = validity;
        }
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
