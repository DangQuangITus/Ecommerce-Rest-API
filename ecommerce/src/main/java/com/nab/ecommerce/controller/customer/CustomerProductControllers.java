package com.nab.ecommerce.controller.customer;

import com.nab.ecommerce.dto.product.ProductDto;
import com.nab.ecommerce.payload.response.PagedResponse;
import com.nab.ecommerce.repositories.UserProfileRepository;
import com.nab.ecommerce.security.CurrentUser;
import com.nab.ecommerce.security.UserPrincipal;
import com.nab.ecommerce.services.CategoryService;
import com.nab.ecommerce.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product")
public class CustomerProductControllers {

  private static final Logger logger = LoggerFactory.getLogger(CustomerProductControllers.class);

  @Autowired
  ProductService productService;
  @Autowired
  CategoryService categoryService;

  @Autowired
  private UserProfileRepository userRepository;

  @GetMapping()
  public PagedResponse<ProductDto> listProduct(@CurrentUser UserPrincipal userPrincipal,
      @RequestBody ProductDto productDto) {

    PagedResponse<ProductDto> list = productService.listProducts(userPrincipal, productDto);

    return list;
  }


  @GetMapping("/search")
  public PagedResponse<ProductDto> searchProduct(@CurrentUser UserPrincipal userPrincipal,
      @RequestBody ProductDto productDto) {

    PagedResponse<ProductDto> list = productService.searchProducts(userPrincipal, productDto);

    return list;
  }

}
