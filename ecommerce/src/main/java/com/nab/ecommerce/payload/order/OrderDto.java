package com.nab.ecommerce.payload.order;

import com.nab.ecommerce.models.order.Order;
import java.util.List;
import javax.validation.constraints.NotNull;

public class OrderDto {

  private Integer id;
  private Long userId;
  private @NotNull String fullName;
  private @NotNull String email;
  private @NotNull String phone;
  private @NotNull String address;
  private @NotNull List<OrderItemsDto> orderItemsDtos;


  public OrderDto() {
  }

  public OrderDto(Order order) {
    this.setId(order.getId());
    this.setUserId(order.getCreatedBy());
  }

  public List<OrderItemsDto> getOrderItemsDtos() {
    return orderItemsDtos;
  }

  public void setOrderItemsDtos(List<OrderItemsDto> orderItemsDtos) {
    this.orderItemsDtos = orderItemsDtos;
  }

  public double getTotalPrices() {
    double total = 0;
    for (OrderItemsDto item : orderItemsDtos) {
      total += item.getPrice() * item.getQuantity();
    }

    return total;
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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
