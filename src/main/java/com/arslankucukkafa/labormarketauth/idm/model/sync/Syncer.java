package com.arslankucukkafa.labormarketauth.idm.model.sync;

import com.arslankucukkafa.labormarketauth.idm.model.UserModel;
import org.springframework.security.oauth2.core.user.OAuth2User;
public interface Syncer {
    UserModel sync(OAuth2User oAuth2User);

    UserModel updateModel(UserModel currentModel, UserModel aouthModel);

}
