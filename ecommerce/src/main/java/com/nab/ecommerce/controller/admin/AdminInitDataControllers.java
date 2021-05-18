package com.nab.ecommerce.controller.admin;

import com.nab.ecommerce.enums.RoleName;
import com.nab.ecommerce.exception.AppException;
import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.models.Role;
import com.nab.ecommerce.models.user.UserProfile;
import com.nab.ecommerce.payload.response.ApiResponse;
import com.nab.ecommerce.repositories.RoleRepository;
import com.nab.ecommerce.repositories.UserProfileRepository;
import com.nab.ecommerce.services.BrandService;
import com.nab.ecommerce.services.CategoryService;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/init")
public class AdminInitDataControllers {

  private static final Logger logger = LoggerFactory.getLogger(AdminInitDataControllers.class);

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  UserProfileRepository userProfileRepository;
  @Autowired
  CategoryService categoryService;
  @Autowired
  BrandService brandService;

  @PostMapping
  public ResponseEntity<ApiResponse> initData() {

    if (!roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()) {
      roleRepository.save(new Role(RoleName.ROLE_ADMIN));
    }
    if (!roleRepository.findByName(RoleName.ROLE_USER).isPresent()) {
      roleRepository.save(new Role(RoleName.ROLE_USER));
    }

    if (!userProfileRepository.existsByUsername("admin")) {
      UserProfile user = new UserProfile("admin", "admin@gmail.com", "admin", "admin", "0396424711");
      user.setPassword(passwordEncoder.encode("admin"));
      Role role = roleRepository.findByName(RoleName.ROLE_ADMIN)
          .orElseThrow(() -> new AppException(("Role not set")));
      user.setRoles(Collections.singleton(role));
      userProfileRepository.save(user);
    }

    if (!categoryService.readCategory(1).isPresent()) {
      Category category = new Category("fruit", "Trai cay");
      category.setId(1);
      categoryService.createCategory(category);
    }
    if (!brandService.readBrand(1).isPresent()) {
      Brand brand = new Brand("VN", "Viet Nam");
      brand.setId(1);
      brandService.createBrand(brand);
    }

    return new ResponseEntity<>(new ApiResponse(true, "Data has been added"), HttpStatus.CREATED);
  }

}
