package com.arslankucukkafa.labormarketauth.idm.auth.model.sync;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class GithubSyncer implements Syncer {
    @Override
    public UserModel userSync(OAuth2User oAuth2User) {
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
        userModel.setProvider(Arrays.asList(Provider.GITHUB));
        userModel.setRole(new ArrayList<>());

        return userModel;
    }

    @Override
    public AddressModel addressSync(OAuth2User oAuth2User) {
        AddressModel addressModel = new AddressModel();
        // Oauth2User dan genelede adres bilgisi alınmamaktadır. Default olarak Türkiye adresi verilmiştir.
        addressModel.setCountry("Turkey");
        return addressModel;
    }

    @Override
    public ContactModel contactSync(OAuth2User oAuth2User) {
        ContactModel contactModel = new ContactModel();
        contactModel.setGithub_url(oAuth2User.getAttribute("html_url"));
        contactModel.setAvatar_url(oAuth2User.getAttribute("avatar_url"));
        contactModel.setEmail(oAuth2User.getAttribute("email"));
        contactModel.setEmailVerified(true);
        return contactModel;
    }

/*    @Override
    public UserModel updateModel(UserModel currentModel, UserModel oauthModel) {
        currentModel(currentModel.getAvatar_url() == null ? oauthModel.getAvatar_url() : currentModel.getAvatar_url());
        return currentModel;
    }*/

    @Override
    public UserModel updateUserModel(UserModel currentModel, UserModel aouthUserModel) {
        currentModel.getProvider().add(Provider.GITHUB);
        return currentModel;
    }

    @Override
    public ContactModel updateContactModel(ContactModel currentModel, ContactModel aouthContactModel) {
        currentModel.setEmailVerified(currentModel.isEmailVerified() == false ? aouthContactModel.isEmailVerified() : currentModel.isEmailVerified());
        currentModel.setGithub_url(currentModel.getGithub_url() == null ? aouthContactModel.getGithub_url() : currentModel.getGithub_url());
        currentModel.setAvatar_url(currentModel.getAvatar_url() == null ? aouthContactModel.getAvatar_url() : currentModel.getAvatar_url());
        return currentModel;
    }

    @Override
    public AddressModel updateAddressModel(AddressModel currentModel, AddressModel aouthAddressModel) {
        return currentModel;
    }

}
