package com.nab.ecommerce.services;

import com.nab.ecommerce.payload.order.OrderDto;
import com.nab.ecommerce.payload.order.OrderItemsDto;
import com.nab.ecommerce.exception.BadRequestException;
import com.nab.ecommerce.exception.ProductNotExistException;
import com.nab.ecommerce.exception.ProductOutOfStockException;
import com.nab.ecommerce.models.order.Order;
import com.nab.ecommerce.models.product.Product;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.payload.response.ApiResponse;
import com.nab.ecommerce.repositories.OrderRepository;
import com.nab.ecommerce.repositories.ProductStatusRepository;
import com.nab.ecommerce.repositories.UserRepository;
import com.nab.ecommerce.security.UserPrincipal;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class OrderService {

  private static final Logger logger = LogManager.getLogger(OrderService.class);

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductService productService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  OrderItemsService orderItemsService;
  @Autowired
  ProductStatusRepository productStatusRepository;

  public Order saveOrder(OrderDto orderDto, User user) {
    Order order = getOrderFromDto(orderDto);
    user.setOrders(Collections.singletonList(order));
    User userRes = userRepository.save(user);
    order.setUserId(userRes.getId());
    return orderRepository.save(order);
  }

  private Order getOrderFromDto(OrderDto orderDto) {
    Order order = new Order(orderDto);
    return order;
  }
  private Order getOrderFromDtoAndUser(OrderDto orderDto, User user) {
    Order order = new Order(orderDto, user);
    return order;
  }

  public void validateOrderDTO(OrderDto orderDto) {

    if (orderDto.getOrderItemsDtos() == null || orderDto.getOrderItemsDtos().isEmpty()) {
      throw new BadRequestException("Request Data invalid: order items is empty.");
    }
    if (StringUtils.isEmpty(orderDto.getAddress())) {
      throw new BadRequestException("Request Data invalid: Address is empty.");
    }
    if (StringUtils.isEmpty(orderDto.getEmail())) {
      throw new BadRequestException("Request Data invalid: Email is empty.");
    }

    if (StringUtils.isEmpty(orderDto.getPhone())) {
      throw new BadRequestException("Request Data invalid: Phone is empty.");
    }

    if (StringUtils.isEmpty(orderDto.getFullName())) {
      throw new BadRequestException("Request Data invalid: Fullname is empty.");
    }

    validateOrderItems(orderDto.getOrderItemsDtos());

  }

  public ApiResponse placeOrder(OrderDto orderDto, UserPrincipal userPrincipal) {

    try {

      // validata request data
      validateOrderDTO(orderDto);

      User user = getUserFromDto(orderDto);
      Order res = saveOrder(orderDto, user);
      return new ApiResponse(true, String.format("place order successful with id %s", orderDto.getId()));

    } catch (Exception e) {
      logger.error(String.format("Place order error: %s", e.getMessage()));
      return new ApiResponse(false, String.format("Place order error: %s", e.getMessage()));
    }
  }

  public User getUserFromDto(OrderDto orderDto) {
    User user = new User();
    user.setAddress(orderDto.getAddress());
    user.setEmail(orderDto.getEmail());
    user.setPhone(orderDto.getPhone());
    user.setFullName(orderDto.getFullName());
    return user;
  }

  private void validateOrderItems(List<OrderItemsDto> itemsDtos) {

    for (OrderItemsDto item : itemsDtos) {

      if (item.getProductId() < 0) {
        throw new BadRequestException(
            String.format("Request Data invalid: product is invalid with id %s", item.getProductId()));
      }

      try {
        Product product = productService.getProductById(item.getProductId());
        if (product.getStock() < item.getQuantity()) {
          throw new ProductOutOfStockException(product.getName());
        }
      } catch (ProductNotExistException e) {
        throw new ProductNotExistException("Product not found");
      }

    }
  }

}


