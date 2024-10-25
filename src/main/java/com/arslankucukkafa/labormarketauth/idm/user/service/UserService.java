package com.arslankucukkafa.labormarketauth.idm.user.service;

import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.user.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    private static final Log logger = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findUserModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public UserModel saveUser(UserModel userModel) {
        try {
            return userRepository.save(userModel);
        } catch (Exception e) {
            logger.error("Error occured while saving user: " + e.getMessage());
            return null;
        }
    }

    public Optional<UserModel> findUserModel (UserModel  userModel) {
        return userRepository.findUserModelByUsername(userModel.getUsername());
    }


}
