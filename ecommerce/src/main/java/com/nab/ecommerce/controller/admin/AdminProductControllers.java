package com.nab.ecommerce.controller.admin;

import com.nab.ecommerce.exception.AuthoException;
import com.nab.ecommerce.exception.BadRequestException;
import com.nab.ecommerce.exception.ProductNotExistException;
import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.models.product.Product;
import com.nab.ecommerce.payload.product.ProductDto;
import com.nab.ecommerce.payload.response.ApiResponse;
import com.nab.ecommerce.services.BrandService;
import com.nab.ecommerce.services.CategoryService;
import com.nab.ecommerce.services.ProductService;
import java.io.IOException;
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

    try {

      try {
        productService.validateProductDto(productDto);
      } catch (BadRequestException e) {
        return new ResponseEntity<>(new ApiResponse(false, "Add Product failed, validate request error."),
            HttpStatus.BAD_REQUEST);
      }

      Brand brand = brandService.readBrand(productDto.getBrandId()).get();
      Category category = categoryService.readCategory(productDto.getCategoryId()).get();
      Product product = productService.addProduct(productDto, category, brand);

      return new ResponseEntity<>(
          new ApiResponse(true, String.format("Product has been added with id %s", product.getId())),
          HttpStatus.CREATED);

    } catch (AuthoException e) {
      return new ResponseEntity<>(new ApiResponse(false, String.format("Add Product exception: %s", e.getMessage())),
          HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    } catch (Exception e) {
      logger.error(String.format("Add product error: %s", e.getMessage()));
      return new ResponseEntity<>(new ApiResponse(false, String.format("Add Product exception: %s", e.getMessage())),
          HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/update/{productID}")
  @PreAuthorize(value = "hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID,
      @RequestBody @Valid ProductDto productDto) {

    try {
      productService.validateProductDto(productDto);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(new ApiResponse(false, "Update Product failed, validate request error."),
          HttpStatus.BAD_REQUEST);
    }
    Brand brand = brandService.readBrand(productDto.getBrandId()).get();
    Category category = categoryService.readCategory(productDto.getCategoryId()).get();

    productService.updateProduct(productID, productDto, category, brand);

    return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
  }


  @PostMapping("/delete/{productID}")
  @PreAuthorize(value = "hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productID") Integer productID) {

    try {

      productService.deleteProduct(productID);
      return new ResponseEntity<>(new ApiResponse(true, "Product has been deleted"), HttpStatus.OK);
    } catch (ProductNotExistException ex) {
      return new ResponseEntity<>(new ApiResponse(false, "Product delete fail, error: product not found."),
          HttpStatus.BAD_REQUEST);
    }
  }
}
