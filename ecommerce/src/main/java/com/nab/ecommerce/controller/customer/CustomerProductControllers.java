package com.nab.ecommerce.controller.customer;

import com.nab.ecommerce.payload.product.ProductDto;
import com.nab.ecommerce.payload.response.PagedResponse;
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

  @GetMapping("")
  public PagedResponse<ProductDto> listProduct(@RequestBody ProductDto productDto) {

    return productService.listProducts(productDto);
  }

  @GetMapping("/search")
  public PagedResponse<ProductDto> searchProduct(@RequestBody ProductDto productDto) {

    return productService.searchProducts(productDto);

  }

}
