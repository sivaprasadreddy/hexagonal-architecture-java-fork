package eu.happycoders.shop.service.cart;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.model.customer.CustomerId;

import java.util.Objects;

/**
 * Use case implementation: Emptying a shopping cart.
 *
 * @author Sven Woltmann
 */
public class EmptyCartUseCase {

  private final CartRepository cartRepository;

  public EmptyCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public void emptyCart(CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");

    cartRepository.deleteByCustomerId(customerId);
  }
}
