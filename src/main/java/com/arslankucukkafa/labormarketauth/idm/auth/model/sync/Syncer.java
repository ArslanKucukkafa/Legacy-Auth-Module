package com.arslankucukkafa.labormarketauth.idm.auth.model.sync;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import org.springframework.security.oauth2.core.user.OAuth2User;
public interface Syncer {
    UserModel userSync(OAuth2User oAuth2User);
    ContactModel contactSync(OAuth2User oAuth2User);
    AddressModel addressSync(OAuth2User oAuth2User);

    UserModel updateUserModel(UserModel currentModel, UserModel aouthModel);
    ContactModel updateContactModel(ContactModel currentModel, ContactModel aouthModel);
    AddressModel updateAddressModel(AddressModel currentModel, AddressModel aouthModel);

}
