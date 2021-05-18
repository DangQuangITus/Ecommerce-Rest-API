package com.nab.ecommerce.controller;

import com.nab.ecommerce.enums.RoleName;
import com.nab.ecommerce.exception.AppException;
import com.nab.ecommerce.models.Role;
import com.nab.ecommerce.models.user.UserProfile;
import com.nab.ecommerce.payload.request.LoginRequest;
import com.nab.ecommerce.payload.request.SignupRequest;
import com.nab.ecommerce.payload.response.ApiResponse;
import com.nab.ecommerce.payload.response.JwtAuthenticationResponse;
import com.nab.ecommerce.repositories.RoleRepository;
import com.nab.ecommerce.repositories.UserProfileRepository;
import com.nab.ecommerce.security.JWTTokenProvider;
import java.net.URI;
import java.util.Collections;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthControllers {

  private static final Logger logger = LogManager.getLogger(AuthControllers.class);

  @Autowired
  UserProfileRepository userProfileRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  JWTTokenProvider jwtTokenProvider;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  AuthenticationManager authenticationManager;

  @PostMapping("/signin")
  public ResponseEntity<?> authenUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtTokenProvider.geneToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }


  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signupRequest) {

    if (userProfileRepository.existsByUsername(signupRequest.getUsername())) {
      logger.error("Username is already taken!");
      return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
          HttpStatus.BAD_REQUEST);
    }

    if (userProfileRepository.existsByEmail(signupRequest.getEmail())) {
      logger.error("Email Address already in use.");
      return new ResponseEntity(new ApiResponse(false, "Email Address already in use."),
          HttpStatus.BAD_REQUEST);
    }

    UserProfile user = new UserProfile(signupRequest.getUsername(), signupRequest.getEmail(),
        signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getPhoneNo());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

    Role role = roleRepository.findByName(RoleName.ROLE_USER)
        .orElseThrow(() -> new AppException(("Role not set")));
    user.setRoles(Collections.singleton(role));

    UserProfile res = userProfileRepository.save(user);

    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/api/users/{username}")
        .buildAndExpand(res.getUsername()).toUri();

    return ResponseEntity.created(location).body(
        new ApiResponse(true, "Register user successfull"));


  }
}
