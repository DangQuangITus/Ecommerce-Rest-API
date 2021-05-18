package com.nab.ecommerce.services;

import com.nab.ecommerce.dto.cart.CartDto;
import com.nab.ecommerce.dto.cart.CartItemDto;
import com.nab.ecommerce.dto.order.OrderDto;
import com.nab.ecommerce.dto.order.PlaceOrderDto;
import com.nab.ecommerce.models.Order;
import com.nab.ecommerce.models.OrderItem;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.repositories.OrderRepository;
import com.nab.ecommerce.repositories.UserRepository;
import com.nab.ecommerce.security.UserPrincipal;
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
  private CartService cartService;

  @Autowired
  UserRepository userRepository;
  @Autowired
  OrderItemsService orderItemsService;


  public Order saveOrder(PlaceOrderDto orderDto, User user) {
    Order order = getOrderFromDto(orderDto, user);
    return orderRepository.save(order);
  }

  private Order getOrderFromDto(PlaceOrderDto orderDto, User user) {
    Order order = new Order(orderDto, user);
    return order;
  }

  private User getUserInfoFromOrderReq(OrderDto orderDto, User user) {

    user.setEmail(orderDto.getEmail());
    user.setPhone(orderDto.getPhone());
    user.setAddress(orderDto.getAddress());
    return user;
  }

  public void placeOrder(UserPrincipal user, OrderDto orderDto) {
    CartDto cartDto = cartService.listCartItems(user);
    User user1 = userRepository.findByUsername(user.getUsername()).get();
    user1 = getUserInfoFromOrderReq(orderDto, user1);
    PlaceOrderDto placeOrderDto = new PlaceOrderDto();
    placeOrderDto.setUser(user1);
    placeOrderDto.setTotalPrice(cartDto.getTotalCost());

    Order newOrder = saveOrder(placeOrderDto, user1);

    List<CartItemDto> cartItemDtoList = cartDto.getcartItems();
    for (CartItemDto cartItemDto : cartItemDtoList) {
      OrderItem orderItem = new OrderItem(
          newOrder,
          cartItemDto.getProduct(),
          cartItemDto.getQuantity(),
          cartItemDto.getProduct().getPrice());
      orderItemsService.addOrderedProducts(orderItem);
    }
    cartService.deleteUserCartItems(user);
  }

}


