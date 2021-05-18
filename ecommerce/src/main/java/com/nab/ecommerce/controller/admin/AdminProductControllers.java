package com.nab.ecommerce.controller.admin;

import com.nab.ecommerce.dto.product.ProductDto;
import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.payload.response.ApiResponse;
import com.nab.ecommerce.services.BrandService;
import com.nab.ecommerce.services.CategoryService;
import com.nab.ecommerce.services.ProductService;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/product")
public class AdminProductControllers {

  private static final Logger logger = LoggerFactory.getLogger(AdminProductControllers.class);

  @Autowired
  ProductService productService;
  @Autowired
  CategoryService categoryService;
  @Autowired
  BrandService brandService;

  @PostMapping("/add")
  @PreAuthorize(value = "hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody ProductDto productDto) {

    Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
    Optional<Brand> optionalBrand = brandService.readBrand(productDto.getBrandId());

    if (!optionalCategory.isPresent()) {
      logger.error("category is invalid");
      return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
    }

    if (!optionalBrand.isPresent()) {
      logger.error("brand is invalid");
      return new ResponseEntity<>(new ApiResponse(false, "brand is invalid"), HttpStatus.CONFLICT);
    }

    Brand brand = optionalBrand.get();
    Category category = optionalCategory.get();

    productService.addProduct(productDto, category, brand);

    return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
  }

  @PostMapping("/update/{productID}")
  @PreAuthorize(value = "hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID,
      @RequestBody @Valid ProductDto productDto) {

    Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
    if (!optionalCategory.isPresent()) {
      return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
    }
    Category category = optionalCategory.get();

    productService.updateProduct(productID, productDto, category);

    return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
  }


  @PostMapping("/delete/{productID}")
  @PreAuthorize(value = "hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productID") Integer productID,
      @RequestBody @Valid ProductDto productDto) {

    Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
    if (!optionalCategory.isPresent()) {
      return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
    }
    Category category = optionalCategory.get();

    productService.updateProduct(productID, productDto, category);

    return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
  }
}
