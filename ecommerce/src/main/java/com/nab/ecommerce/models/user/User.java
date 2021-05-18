package com.nab.ecommerce.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nab.ecommerce.models.Order;
import com.nab.ecommerce.models.audit.DateAudit;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    })
public class User extends DateAudit {


  public User() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 40)
  private String username;

  @Size(max = 11)
  private String phone;

  private String address;

  @NaturalId
  @NotBlank
  @Email
  @Size(max = 40)
  String email;

  @JsonIgnore
  @OneToMany(mappedBy = "user",
      fetch = FetchType.LAZY)
  private List<Order> orders;

  public User(@NotBlank @Size(max = 40) String username, @NotBlank @Email @Size(max = 40) String email,
      List<Order> orders) {

    this.username = username;
    this.email = email;
    this.orders = orders;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }


}
