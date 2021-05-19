package com.nab.ecommerce.services;

import com.nab.ecommerce.dto.order.OrderDto;
import com.nab.ecommerce.dto.order.OrderItemsDto;
import com.nab.ecommerce.exception.BadRequestException;
import com.nab.ecommerce.exception.ProductNotExistException;
import com.nab.ecommerce.exception.ProductOutOfStockException;
import com.nab.ecommerce.models.order.Order;
import com.nab.ecommerce.models.product.Product;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.repositories.OrderRepository;
import com.nab.ecommerce.repositories.ProductStatusRepository;
import com.nab.ecommerce.repositories.UserRepository;
import com.nab.ecommerce.security.UserPrincipal;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class OrderService {

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
    Order order = getOrderFromDto(orderDto, user);
    user.setOrders(Collections.singletonList(order));
    userRepository.save(user);
    return orderRepository.save(order);
  }

  private Order getOrderFromDto(OrderDto orderDto, User user) {
    Order order = new Order(orderDto, user);
    return order;
  }

  public Order placeOrder(OrderDto orderDto, UserPrincipal userPrincipal) {

    if (orderDto.getOrderItemsDtos() == null || orderDto.getOrderItemsDtos().isEmpty()) {
      throw new BadRequestException("Request Data invalid: order items is empty.");
    }
    List<OrderItemsDto> itemsDtos = orderDto.getOrderItemsDtos();

    try {
      validateOrderItems(itemsDtos);
    } catch (BadRequestException | ProductNotExistException | ProductOutOfStockException e) {
      throw e;
    }
    User user = getUserFromDto(orderDto);
    return saveOrder(orderDto, user);
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
        throw new BadRequestException("Request Data invalid.");
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


