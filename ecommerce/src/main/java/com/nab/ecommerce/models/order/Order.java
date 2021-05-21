package com.nab.ecommerce.models.order;

import com.nab.ecommerce.models.audit.DateAudit;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.payload.order.OrderDto;
import com.nab.ecommerce.payload.order.OrderItemsDto;
import com.nab.ecommerce.utils.GsonUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "total_price")
  private Double totalPrice;

  @Column(name = "address")
  private String address;

  @Column(name = "user_id")
  private long userId;

  @Column(name = "order_items")
  private String orderItemsJson;

  public Order(OrderDto orderDto, User user) {

    this.address = this.getAddress();
    this.totalPrice = orderDto.getTotalPrices();
    this.userId = user.getId();
    List<OrderItem> orderItems = new ArrayList<>();

    for (OrderItemsDto itemsDto : orderDto.getOrderItemsDtos()) {
      orderItems.add(new OrderItem(itemsDto));
    }
    this.orderItemsJson = GsonUtil.toJsonString(orderItems);
  }

  public Order(OrderDto orderDto) {

    this.address = this.getAddress();
    this.totalPrice = orderDto.getTotalPrices();
    List<OrderItem> orderItems = new ArrayList<>();

    for (OrderItemsDto itemsDto : orderDto.getOrderItemsDtos()) {
      orderItems.add(new OrderItem(itemsDto));
    }
    this.orderItemsJson = GsonUtil.toJsonString(orderItems);

  }

  public Order() {
  }

  public String getOrderItemsJson() {
    return orderItemsJson;
  }

  public void setOrderItemsJson(String orderItemsJson) {
    this.orderItemsJson = orderItemsJson;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

}