package com.arslankucukkafa.labormarketauth.idm.auth.model.sync;

import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class GoogleSyncer implements Syncer{

    @Override
    public UserModel sync(OAuth2User oAuth2User) {
        UserModel userModel = new UserModel();
        ContactModel contactModel = new ContactModel();
        contactModel.setEmail(oAuth2User.getAttribute("email"));
        userModel.setUsername(oAuth2User.getAttribute("name"));
        userModel.setName(oAuth2User.getAttribute("given_name"));
        userModel.setSurname(oAuth2User.getAttribute("family_name"));
        userModel.setContact(contactModel);
        userModel.setAvatar_url(oAuth2User.getAttribute("picture"));
        return userModel;
    }

    @Override
    public UserModel updateModel(UserModel currentModel, UserModel aouthModel) {
        currentModel.setAvatar_url(currentModel.getAvatar_url() == null ? aouthModel.getAvatar_url() : currentModel.getAvatar_url());
        return currentModel;
    }
}
