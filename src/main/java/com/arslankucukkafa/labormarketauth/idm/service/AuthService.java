package com.arslankucukkafa.labormarketauth.idm.service;

import com.arslankucukkafa.labormarketauth.idm.model.DTO.JwtDto;
import com.arslankucukkafa.labormarketauth.idm.model.DTO.UserLoginDto;
import com.arslankucukkafa.labormarketauth.idm.model.DTO.UserRegisterDto;
import com.arslankucukkafa.labormarketauth.idm.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.idm.repository.UserRepository;
import com.arslankucukkafa.labormarketauth.util.JwtService;
import com.arslankucukkafa.labormarketauth.util.email.EmailAuthConfigurationProperties;
import com.arslankucukkafa.labormarketauth.util.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthService {

    @Value("${app.default.role}")
    private String defaultRole;

    @Value("${spring.application.base-url}")
    private String baseUrl;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailAuthConfigurationProperties emailAuthConfigurationProperties;
    @Autowired
    private AuthenticationManager authenticationManager;



    public void saveUser(UserRegisterDto userDTO) {
        UserModel userModel = userDTO.toUserModel();

        if (checkIfUserExists(userModel)) {
            throw new RuntimeException("User already exists");
        } else {
            RoleModel defaultRoleModel = roleRepository.findByName(defaultRole).orElseThrow(() -> new RuntimeException("Default role not found"));
            userModel.getRole().add(defaultRoleModel);
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

            String token = jwtService.generateToken(userModel.getContact().getEmail(),
                    emailAuthConfigurationProperties.getRegister().getSecret(),
                    emailAuthConfigurationProperties.getRegister().getValidity());

            String url = baseUrl + "/auth/confirm?token=" + token;
            try {
                emailService.sendConfirmationLinkEmail(userModel.getContact().getEmail(), url);
            } catch (Exception e) {
                throw new RuntimeException("Error while sending confirmation email");
            }
            userRepository.save(userModel);

        }

    }

    public ResponseEntity<String> signIn(UserLoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

        if(authentication.isAuthenticated()) {
            UserModel userModel = (UserModel) authentication.getPrincipal();
            var token = jwtService.generateToken(loginDto.getUsername(), emailAuthConfigurationProperties.getLogin().getSecret(), emailAuthConfigurationProperties.getLogin().getValidity());
            emailService.sendAuthenticateEmail(userModel.getContact().getEmail(), token);
            return ResponseEntity.ok("Login code send to your email adress");
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }


    private boolean checkIfUserExists(UserModel userModel) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("username").is(userModel.getUsername()),
                Criteria.where("contact.email").is(userModel.getContact()),
                Criteria.where("contact.phone").is(userModel.getContact())
        );
        query.addCriteria(criteria);

        return mongoTemplate.exists(query, UserModel.class);


    }


}