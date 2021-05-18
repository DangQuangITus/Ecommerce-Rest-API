package com.nab.ecommerce.services;

import com.nab.ecommerce.dto.cart.AddToCartDto;
import com.nab.ecommerce.dto.cart.CartDto;
import com.nab.ecommerce.dto.cart.CartItemDto;
import com.nab.ecommerce.models.Cart;
import com.nab.ecommerce.models.Product;
import com.nab.ecommerce.models.user.User;
import com.nab.ecommerce.repositories.CartRepository;
import com.nab.ecommerce.security.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

  @Autowired
  private CartRepository cartRepository;

  public CartService() {
  }

  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public void addToCart(AddToCartDto addToCartDto, Product product, UserPrincipal user) {
    Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
    cartRepository.save(cart);
  }


  public CartDto listCartItems(UserPrincipal user) {

    List<Cart> cartList = cartRepository.findAllByCreatedBy(user.getId());
    List<CartItemDto> cartItems = new ArrayList<>();
    for (Cart cart : cartList) {
      CartItemDto cartItemDto = getDtoFromCart(cart);
      cartItems.add(cartItemDto);
    }
    double totalCost = 0;
    for (CartItemDto cartItemDto : cartItems) {
      totalCost += (cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
    }
    CartDto cartDto = new CartDto(cartItems, totalCost);
    return cartDto;
  }


  public static CartItemDto getDtoFromCart(Cart cart) {
    CartItemDto cartItemDto = new CartItemDto(cart);
    return cartItemDto;
  }

//    public void updateCartItem(AddToCartDto cartDto, User user,Product product){
//        Cart cart = cartRepository.getOne(cartDto.getId());
//        cart.setQuantity(cartDto.getQuantity());
//        cart.setCreatedDa(new Date());
//        cartRepository.save(cart);
//    }
//
//    public void deleteCartItem(int id,int userId) throws CartItemNotExistException {
//        if (!cartRepository.existsById(id))
//            throw new CartItemNotExistException("Cart id is invalid : " + id);
//        cartRepository.deleteById(id);
//
//    }
//
//    public void deleteCartItems(int userId) {
//        cartRepository.deleteAll();
//    }


  public void deleteUserCartItems(UserPrincipal user) {
    cartRepository.deleteByCreatedBy(user.getId());
  }
}


