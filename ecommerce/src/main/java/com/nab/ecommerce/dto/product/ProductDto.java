package com.nab.ecommerce.dto.product;

import com.nab.ecommerce.models.Product;
import com.nab.ecommerce.payload.request.SearchRequest;
import javax.validation.constraints.NotNull;

public class ProductDto {

  private Integer id;
  private @NotNull String name;
  private @NotNull double price;
  private @NotNull String description;
  private @NotNull String color;
  private @NotNull Integer categoryId;
  private @NotNull Integer brandId;

  public SearchRequest searchRequest;

  public ProductDto(Product product) {
    this.setId(product.getId());
    this.setName(product.getName());
    this.setDescription(product.getDescription());
    this.setColor(product.getColor());
    this.setPrice(product.getPrice());
    this.setCategoryId(product.getCategory().getId());
    this.setBrandId(product.getBrand().getId());
  }

  public ProductDto(@NotNull String name, @NotNull double price, @NotNull String description,
      @NotNull String color, @NotNull Integer categoryId, @NotNull Integer brandId) {

    this.name = name;
    this.price = price;
    this.description = description;
    this.color = color;
    this.categoryId = categoryId;
    this.brandId = brandId;
  }

  public ProductDto() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Integer getBrandId() {
    return brandId;
  }

  public void setBrandId(Integer brandId) {
    this.brandId = brandId;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }
}
