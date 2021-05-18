package com.nab.ecommerce.repositories;

import com.nab.ecommerce.models.Order;
import com.nab.ecommerce.models.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
//    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

}
