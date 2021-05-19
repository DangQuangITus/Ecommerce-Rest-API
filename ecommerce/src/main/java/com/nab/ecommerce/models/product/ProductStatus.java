package com.nab.ecommerce.models.product;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_status")
public class ProductStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private @NotNull
  int productId;
  private boolean isAvailable;
  private boolean isRisk;
  private @NotNull long stock;

  public ProductStatus(int productId, long stock) {
    this.productId = productId;
    this.isAvailable = stock > 0 ? true : false;
    this.isRisk = stock < 10 ? true : false;
    this.stock = stock;
  }


  public ProductStatus(Product product) {
    this.isAvailable = product.getStock() > 0;
    this.isRisk = product.getStock() < 10 ? true : false;
    this.stock = product.getStock();
  }

  public ProductStatus() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public long getStock() {
    return stock;
  }

  public void setStock(long stock) {
    this.stock = stock;
  }

  public boolean isRisk() {
    return isRisk;
  }

  public void setRisk(boolean risk) {
    isRisk = risk;
  }
}
