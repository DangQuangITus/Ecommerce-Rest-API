package com.nab.ecommerce.models.order;

import com.nab.ecommerce.models.audit.DateAudit;
import com.nab.ecommerce.payload.order.OrderItemsDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private @NotNull int quantity;

  private @NotNull double price;

  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "product_id")
  private Integer productId;

  public OrderItem() {
  }


  public OrderItem(OrderItemsDto itemsDto) {
    this.productId = itemsDto.getProductId();
    this.quantity = itemsDto.getQuantity();
    this.price = itemsDto.getPrice();
  }

  public OrderItem(Integer orderId, @NotNull Integer productId, @NotNull int quantity, @NotNull double price) {
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
    this.orderId = orderId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}