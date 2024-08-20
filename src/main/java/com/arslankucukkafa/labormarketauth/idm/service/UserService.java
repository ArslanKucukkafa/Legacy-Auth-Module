package com.arslankucukkafa.labormarketauth.idm.service;

import com.arslankucukkafa.labormarketauth.idm.model.DTO.LoginDto;
import com.arslankucukkafa.labormarketauth.idm.model.DTO.RegisterDto;
import com.arslankucukkafa.labormarketauth.idm.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.model.UserModel;
import com.arslankucukkafa.labormarketauth.idm.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.idm.repository.UserRepository;
import com.arslankucukkafa.labormarketauth.idm.security.JwtService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private MongoTemplate mongoTemplate;
    private JwtService jwtService;
    public UserService() {}
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserModelById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public void saveUser(RegisterDto userDTO) {
        UserModel userModel = userDTO.toUserModel();

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("username").is(userModel.getUsername()),
                Criteria.where("address").is(userModel.getAddress()),
                Criteria.where("contact").is(userModel.getContact())
        );
        query.addCriteria(criteria);

        boolean exists = mongoTemplate.exists(query, UserModel.class);

        if (exists) {
            throw new RuntimeException("User already exists");
        }
        var roleModel = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        userModel.setRole(roleModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(userModel);
    }

    public ResponseEntity<String> signIn(LoginDto loginDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.username(),loginDto.password());
        if(authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        return ResponseEntity.ok("User signed in");
    }
}
