package com.nab.ecommerce.controller.admin;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nab.ecommerce.payload.product.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@TestPropertySource(locations = "/application.yml")
public class AdminProductControllersTest {

  ProductDto productDto;

  @Autowired
  AdminProductControllers controllers;
  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;


  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();

    productDto = new ProductDto();
    productDto.setBrandId(1);
    productDto.setCategoryId(1);
    productDto.setPrice(1000);
    productDto.setColor("red");
    productDto.setDescription("test");
    productDto.setName("test");
    productDto.setStock(10);
  }

  @Test
  @WithMockUser("admin")
  void addProduct() throws Exception {
    this.mvc
        .perform(
            post("/api/admin/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                    + "    \"name\": \"Cam\",\n"
                    + "    \"price\": 1500,\n"
                    + "    \"description\": \"Cam ne\",\n"
                    + "    \"stock\": 1000,\n"
                    + "    \"color\": \"orange\",\n"
                    + "    \"categoryId\": \"1\",\n"
                    + "    \"brandId\": \"1\"\n"
                    + "}")
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))

        )
        .andExpect(status().isBadRequest());
//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    headers.setBearerAuth(
//        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjIxNDQ1MDQyLCJleHAiOjE2MjIwNDk4NDJ9.9PkZ_hh-eGFOlqFyuFNK-KA-RlnHuxikNmzwjTu7QPI");
//    HttpEntity<String> request = new HttpEntity<>(productDto.toString(), headers);
//    String result = restTemplate.postForObject("http://localhost:9000/api/admin/product/add", request, String.class);
//    ResponseEntity<ApiResponse> responseEntity = restTemplate
//        .postForEntity("http://localhost:9000/api/admin/product/add", productDto,
//            ApiResponse.class);

  }

  @Test
  void updateProduct() {
  }

  @Test
  void deleteProduct() {
  }
}