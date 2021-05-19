package com.nab.ecommerce.controller.customer;

import com.nab.ecommerce.dto.order.OrderDto;
import com.nab.ecommerce.payload.response.ApiResponse;
import com.nab.ecommerce.repositories.UserProfileRepository;
import com.nab.ecommerce.security.CurrentUser;
import com.nab.ecommerce.security.UserPrincipal;
import com.nab.ecommerce.services.CategoryService;
import com.nab.ecommerce.services.OrderService;
import com.nab.ecommerce.services.ProductService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class CustomerOrderControllers {

  private static final Logger logger = LoggerFactory.getLogger(CustomerOrderControllers.class);

  @Autowired
  ProductService productService;
  @Autowired
  CategoryService categoryService;

  @Autowired
  private UserProfileRepository userRepository;
  @Autowired
  private OrderService orderService;


  @PostMapping("/create")
  public ResponseEntity<ApiResponse> placeOrder(@Valid @RequestBody OrderDto orderDto, @CurrentUser UserPrincipal user) {

    orderService.placeOrder(orderDto, user);
    return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
  }

}
