package com.arslankucukkafa.labormarketauth.idm.auth.service;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.adress.service.AddressService;
import com.arslankucukkafa.labormarketauth.idm.auth.model.AuthLoginDto;
import com.arslankucukkafa.labormarketauth.idm.auth.model.AuthRegisterDto;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.contact.service.ContactService;
import com.arslankucukkafa.labormarketauth.idm.role.service.RoleService;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.user.service.UserService;
import com.arslankucukkafa.labormarketauth.util.JwtService;
import com.arslankucukkafa.labormarketauth.util.email.EmailAuthConfigurationProperties;
import com.arslankucukkafa.labormarketauth.util.email.EmailService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static final Log logger = LogFactory.getLog(AuthService.class);

    @Value("${app.default.role}")
    private String defaultRole;
    @Value("${spring.application.base-url}")
    private String baseUrl;
    private RoleService roleService;
    private UserService userService;
    private ContactService contactService;
    private AddressService addressService;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private EmailService emailService;
    private EmailAuthConfigurationProperties emailAuthConfigurationProperties;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder, RoleService
            roleService, UserService userService, ContactService contactService, AddressService addressService, EmailService emailService,
                       EmailAuthConfigurationProperties emailAuthConfigurationProperties, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userService = userService;
        this.contactService = contactService;
        this.addressService = addressService;
        this.emailService = emailService;
        this.emailAuthConfigurationProperties = emailAuthConfigurationProperties;
        this.jwtService = jwtService;
    }

    public void saveUser(AuthRegisterDto authRegisterDto) {
        // map dto to contactModel and userModel
        ContactModel contactModel = authRegisterDto.toContactModel(authRegisterDto);
        UserModel userModel = authRegisterDto.toUserModel(authRegisterDto);
        AddressModel addressModel = authRegisterDto.toAddressModel(authRegisterDto);

        // encode password and set it to userModel
        userModel.setPassword(passwordEncoder.encode(authRegisterDto.getPassword()));
        // get default role

        if (checkIfUserExists(contactModel,userModel).isPresent()) {
            throw new RuntimeException("User already exists");
        } else {
            createUser(contactModel, addressModel, userModel);
            String token = jwtService.generateToken(contactModel.getEmail(),
                    emailAuthConfigurationProperties.getRegister().getSecret(),
                    emailAuthConfigurationProperties.getRegister().getValidity());

            String url = baseUrl + "/auth/confirm?token=" + token;
            try {
                emailService.sendConfirmationLinkEmail(contactModel.getEmail(), url);
            } catch (Exception e) {
                throw new RuntimeException("Error while sending confirmation email");
            }
            userService.saveUser(userModel);
        }

    }

    public ResponseEntity<String> signIn(AuthLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

        if(authentication.isAuthenticated()) {
            UserModel userModel = (UserModel) authentication.getPrincipal();
            var token = jwtService.generateToken(loginDto.getUsername(), emailAuthConfigurationProperties.getLogin().getSecret(), emailAuthConfigurationProperties.getLogin().getValidity());
            ContactModel contactModel = contactService.getContactWithId(userModel.getContact()).orElseThrow(() -> new RuntimeException("Contact not found"));
            emailService.sendAuthenticateEmail(contactModel.getEmail(), token);
            return ResponseEntity.ok("Login code send to your email adress");
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }


    public Optional<UserModel> checkIfUserExists(ContactModel contactModel, UserModel userModel) {
        Optional<ContactModel> contact = contactService.getContactWithProperties(contactModel);
        Optional<UserModel> user = userService.findUserModel(userModel);

        if(contact.isPresent() || user.isPresent()) {
            return user;
        }
        return Optional.empty();
    }

    public UserModel createUser(ContactModel mappedContact, AddressModel mappedAddress, UserModel mappedUser) {
        ContactModel saveContact = contactService.saveContact(mappedContact);
        logger.info("Contact saved");
        AddressModel saveAddress = addressService.saveAddress(mappedAddress);
        logger.info("Address saved");
        mappedUser.setContact(saveContact.getId());
        mappedUser.setAddress(saveAddress.getId());
        // fixme: default role db de bulunmayabilir. Birisi adını degiştirirse yada silerse ne olacak? check edilmeli mi?
        mappedUser.getRoles().add(defaultRole);
        logger.info("Default role found and added to user");
        return userService.saveUser(mappedUser);
    }


}
