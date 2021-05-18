package com.nab.ecommerce.security;

import com.nab.ecommerce.models.user.UserProfile;
import com.nab.ecommerce.repositories.UserProfileRepository;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserProfileRepository userProfileRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

    UserProfile user = userProfileRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() ->
        new UsernameNotFoundException("user not found: " + usernameOrEmail));

    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) throws UserPrincipalNotFoundException {

    UserProfile user = userProfileRepository.findById(id).orElseThrow(() ->
        new UserPrincipalNotFoundException("user not found with id: " + id));

    return UserPrincipal.create(user);
  }


}
