package com.nab.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nab.ecommerce.dto.product.ProductDto;
import com.nab.ecommerce.models.audit.UserDateAudit;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product extends UserDateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private @NotNull String name;
  private String imageURL;
  private @NotNull double price;
  private @NotNull String description;
  private @NotNull String color;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  Category category;


  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "brand_id", nullable = false)
  Brand brand;


  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
  private List<Cart> carts;

  public Product(ProductDto productDto, Category category, Brand brand) {
    this.name = productDto.getName();
    this.description = productDto.getDescription();
    this.price = productDto.getPrice();
    this.category = category;
    this.brand = brand;
  }


  public Product(ProductDto productDto, Category category) {
    this.name = productDto.getName();
    this.description = productDto.getDescription();
    this.price = productDto.getPrice();
    this.category = category;
  }

  public Product(String name, String imageURL, double price, String description, String color, Category category) {
    super();
    this.name = name;
    this.imageURL = imageURL;
    this.price = price;
    this.description = description;
    this.color = color;
    this.category = category;
  }

  public Product() {
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

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
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

  public List<Cart> getCarts() {
    return carts;
  }

  public void setCarts(List<Cart> carts) {
    this.carts = carts;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", imageURL='" + imageURL + '\'' +
        ", price=" + price +
        ", description='" + description + '\'' +
        '}';
  }
}