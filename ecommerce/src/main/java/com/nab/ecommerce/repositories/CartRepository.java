package com.nab.ecommerce.repositories;

import com.nab.ecommerce.models.Cart;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.security.UserPrincipal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByCreatedBy(Long userId);
//
    List<Cart> deleteByCreatedBy(Long userId);

}

