package com.nab.ecommerce.models;

import com.nab.ecommerce.models.audit.DateAudit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orderitems")
public class OrderItem extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;


  @Column(name = "quantity")
  private @NotNull int quantity;

  @Column(name = "price")
  private @NotNull double price;


  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @OneToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  public OrderItem() {
  }

  public OrderItem(Order order, @NotNull Product product, @NotNull int quantity, @NotNull double price) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
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