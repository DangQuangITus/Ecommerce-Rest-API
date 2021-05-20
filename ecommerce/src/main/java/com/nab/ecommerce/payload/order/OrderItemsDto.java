package com.nab.ecommerce.payload.order;

import javax.validation.constraints.NotNull;

public class OrderItemsDto {

  private @NotNull double price;
  private @NotNull int quantity;
  private @NotNull int productId;

  public OrderItemsDto() {
  }

  public OrderItemsDto(@NotNull double price, @NotNull int quantity, @NotNull int productId) {
    this.price = price;
    this.quantity = quantity;
    this.productId = productId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }
}
