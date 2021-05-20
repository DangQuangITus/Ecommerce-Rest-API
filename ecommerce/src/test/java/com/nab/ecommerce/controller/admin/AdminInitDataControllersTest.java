package com.nab.ecommerce.controller.admin;

import static org.junit.jupiter.api.Assertions.*;

import com.nab.ecommerce.enums.RoleName;
import com.nab.ecommerce.models.Role;
import com.nab.ecommerce.repositories.RoleRepository;
import com.nab.ecommerce.repositories.UserProfileRepository;
import com.nab.ecommerce.repositories.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@TestPropertySource(locations = "/application.yml")
class AdminInitDataControllersTest {

  @Autowired
  AdminInitDataControllers controllers;
  @Autowired
  UserProfileRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

  @BeforeEach
  void setUp() {
  }

  @Test
  void initData_success() {
    controllers.initData();
    Assertions.assertTrue(userRepository.existsByUsername("admin"));
  }

  void initData_InsertRole() {

    controllers.initData();
    Assertions.assertTrue(userRepository.existsByUsername("admin"));
  }




}