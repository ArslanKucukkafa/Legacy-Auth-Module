package com.arslankucukkafa.labormarketauth.idm.auth.model.sync;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GoogleSyncer implements Syncer{

    @Override
    public UserModel userSync(OAuth2User oAuth2User) {
        UserModel userModel = new UserModel();
        userModel.setUsername(oAuth2User.getAttribute("name"));
        userModel.setName(oAuth2User.getAttribute("given_name"));
        userModel.setSurname(oAuth2User.getAttribute("family_name"));
        userModel.getProviders().add(Provider.GOOGLE);
        userModel.setRole(new ArrayList<>());
        return userModel;
    }

    @Override
    public ContactModel contactSync(OAuth2User oAuth2User) {
        ContactModel contactModel = new ContactModel();
        contactModel.setAvatar_url(oAuth2User.getAttribute("picture"));
        return null;
    }

    @Override
    public AddressModel addressSync(OAuth2User oAuth2User) {
        return null;
    }

    // ARSLAN.KUCUKKAFA: Update methodlar, Oauthdan alınan bilgilerle mevcut bilgileri güncellemek için kullanılır. Güncelleme işlemi sadece mevcut property boş ise yapılır.
    @Override
    public UserModel updateUserModel(UserModel currentModel, UserModel aouthUserModel) {
        // TODO: Oauthdan user için neler alınabilir?
        currentModel.getProviders().add(Provider.GOOGLE);
        return currentModel;
    }

    @Override
    public AddressModel updateAddressModel(AddressModel currentModel, AddressModel aouthAddressModel) {
        // TODO: Oauthdan user için neler alınabilir?
        return currentModel;
    }

    @Override
    public ContactModel updateContactModel(ContactModel currentModel, ContactModel aouthContactModel) {
        currentModel.setAvatar_url(currentModel.getAvatar_url() == null ? aouthContactModel.getAvatar_url() : currentModel.getAvatar_url());
        currentModel.setEmailVerified(currentModel.isEmailVerified() == false ? aouthContactModel.isEmailVerified() : currentModel.isEmailVerified());
        return currentModel;
    }
}
