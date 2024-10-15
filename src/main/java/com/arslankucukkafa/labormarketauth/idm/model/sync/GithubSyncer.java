package com.arslankucukkafa.labormarketauth.idm.model.sync;

import com.arslankucukkafa.labormarketauth.idm.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.model.UserModel;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GithubSyncer implements Syncer {
    @Override
    public UserModel sync(OAuth2User oAuth2User) {
        UserModel userModel = new UserModel();

        ContactModel contactModel = new ContactModel();
        contactModel.setEmail(oAuth2User.getAttribute("email"));

        String username = oAuth2User.getAttribute("name");
        String[] nameParts = username.split(" ");
        var familyName = nameParts[nameParts.length - 1];
        var givenName = String.join(" ", Arrays.copyOfRange(nameParts, 0, nameParts.length - 1));


        userModel.setUsername(username);
        userModel.setSurname(familyName);
        userModel.setName(givenName);
        userModel.setContact(contactModel);
        userModel.setAvatar_url(oAuth2User.getAttribute("avatar_url"));
        userModel.setGithub_url(oAuth2User.getAttribute("html_url"));

        return userModel;
    }

    @Override
    public UserModel updateModel(UserModel currentModel, UserModel oauthModel) {
        currentModel.setGithub_url(currentModel.getGithub_url() == null ? oauthModel.getGithub_url() : currentModel.getGithub_url());
        currentModel.setAvatar_url(currentModel.getAvatar_url() == null ? oauthModel.getAvatar_url() : currentModel.getAvatar_url());
        return currentModel;
    }

}
