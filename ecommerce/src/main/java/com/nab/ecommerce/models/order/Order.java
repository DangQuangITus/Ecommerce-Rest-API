package com.nab.ecommerce.models.order;

import com.nab.ecommerce.models.audit.DateAudit;
import com.nab.ecommerce.models.audit.UserDateAudit;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.payload.order.OrderDto;
import com.nab.ecommerce.payload.order.OrderItemsDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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


  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItem> orderItems;

  public Order(OrderDto orderDto, User user) {
    orderItems = new ArrayList<>();
    this.address = this.getAddress();
    this.totalPrice = orderDto.getTotalPrices();
    this.userId = user.getId();
    for (OrderItemsDto itemsDto : orderDto.getOrderItemsDtos()) {
      this.orderItems.add(new OrderItem(itemsDto));
    }

  }

  public Order() {
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
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