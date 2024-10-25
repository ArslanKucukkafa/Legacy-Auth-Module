package com.arslankucukkafa.labormarketauth.idm.contact.model;


import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashMap;
import java.util.Map;

public class ContactModel {
        @Id
        private String id;
        @Nullable
        @Indexed(unique = true)
        private String email;
        @Nullable
        private boolean isEmailVerified;
        @Nullable
        @Indexed(unique = true)
        private String phone;
        @Nullable
        @Indexed(unique = true)
        private String github_url;
        @Nullable
        @Indexed(unique = true)
        private String avatar_url;


    // arslan.kucukkafa: Belki kullanıcı kişisel web sitesini, sosyal medya hesaplarını, vb. ek bilgileri eklemek isteyebilir.
        @Nullable
        private Map<String,String> additionalInfo = new HashMap<>();
        public ContactModel(String email, String phone, Map<String, String> additionalInfo, String github_url, String avatar_url) {
            this.email = email;
            this.phone = phone;
            this.additionalInfo = additionalInfo;
            this.github_url = github_url;
            this.avatar_url = avatar_url;
        }
        public ContactModel() {}

        public String getEmail() {
                return email;
            }

        public String getPhone() {
            return phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Map<String, String> getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(Map<String, String> additionalInfo) {
            this.additionalInfo = additionalInfo;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGithub_url() {
            return github_url;
        }

        public void setGithub_url(@Nullable String github_url) {
            this.github_url = github_url;
        }

        @Nullable
        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(@Nullable String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public boolean isEmailVerified() {
            return isEmailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            isEmailVerified = emailVerified;
        }
}
