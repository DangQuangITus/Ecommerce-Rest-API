package com.nab.ecommerce.models;

import com.nab.ecommerce.models.audit.UserDateAudit;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.security.UserPrincipal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart extends UserDateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  private int quantity;

  public Cart() {
  }

  public Cart(Product product, int quantity, UserPrincipal user) {
    this.product = product;
    this.quantity = quantity;
    this.setCreatedBy(user.getId());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
}