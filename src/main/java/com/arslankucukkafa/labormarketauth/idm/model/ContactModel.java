package com.arslankucukkafa.labormarketauth.idm.model;


import jakarta.annotation.Nullable;

import java.util.Map;

public class ContactModel {

        @Nullable
        private String email;
        @Nullable
        private String phone;

        // arslan.kucukkafa: Belki kullanıcı kişisel web sitesini, sosyal medya hesaplarını, vb. ek bilgileri eklemek isteyebilir.

        @Nullable
        private Map<String,String> additionalInfo;
        public ContactModel(String email, String phone, Map<String, String> additionalInfo) {
            this.email = email;
            this.phone = phone;
            this.additionalInfo = additionalInfo;
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
}
