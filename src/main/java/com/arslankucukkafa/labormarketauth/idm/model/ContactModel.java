package com.arslankucukkafa.labormarketauth.idm.model;

import java.util.Map;

public class ContactModel {

        private String email;
        private String phone;
        private AddressModel addressModel;

        // arslan.kucukkafa: Belki kullanıcı kişisel web sitesini, sosyal medya hesaplarını, vb. ek bilgileri eklemek isteyebilir.
        private Map<String,String> additionalInfo;

        public ContactModel(Map<String, String> additionalInfo) {
            this.additionalInfo = additionalInfo;
        }

        public ContactModel(String email, String phone, AddressModel addressModel, Map<String, String> additionalInfo) {
            this.email = email;
            this.phone = phone;
            this.addressModel = addressModel;
            this.additionalInfo = additionalInfo;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public AddressModel getAddressModel() {
            return addressModel;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setAddressModel(AddressModel addressModel) {
            this.addressModel = addressModel;
        }

        public Map<String, String> getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(Map<String, String> additionalInfo) {
            this.additionalInfo = additionalInfo;
        }
}
