package com.nab.ecommerce.dto.cart;

import com.nab.ecommerce.models.Cart;
import com.nab.ecommerce.models.Product;
import javax.validation.constraints.NotNull;

public class CartItemDto {

  private Integer id;
  private @NotNull Long userId;
  private @NotNull Integer quantity;
  private @NotNull Product product;

  public CartItemDto() {
  }

  public CartItemDto(Cart cart) {
    this.setId(cart.getId());
    this.setUserId(cart.getCreatedBy());
    this.setQuantity(cart.getQuantity());
    this.setProduct(cart.getProduct());
  }

  @Override
  public String toString() {
    return "CartDto{" +
        "id=" + id +
        ", userId=" + userId +
        ", quantity=" + quantity +
        ", productName=" + product.getName() +
        '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

}
