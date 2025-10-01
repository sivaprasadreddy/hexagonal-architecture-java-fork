package eu.happycoders.shop.service.cart;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;

import java.util.Objects;

/**
 * Use case implementation: Retrieving a shopping cart.
 *
 * @author Sven Woltmann
 */
public class GetCartUseCase {

  private final CartRepository cartRepository;

  public GetCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Cart getCart(CustomerId customerIdVeryLong) {
    Objects.requireNonNull(customerIdVeryLong, "'customerId' must not be null");

    return cartRepository
        .findByCustomerId(customerIdVeryLong)
        .orElseGet(() -> new Cart(customerIdVeryLong));
  }
}
