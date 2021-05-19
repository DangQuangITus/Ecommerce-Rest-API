package com.nab.ecommerce.models.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nab.ecommerce.dto.order.OrderDto;
import com.nab.ecommerce.models.audit.UserDateAudit;
import com.nab.ecommerce.models.user.User;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends UserDateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "total_price")
  private Double totalPrice;

  @Column(name = "address")
  private String address;

  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItem> orderItems;

  @ManyToOne()
  @JsonIgnore
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public Order() {
  }


  public Order(OrderDto orderDto, User user) {
    this.user = user;
    this.address = user.getAddress();
    this.totalPrice = orderDto.getTotalPrices();
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}