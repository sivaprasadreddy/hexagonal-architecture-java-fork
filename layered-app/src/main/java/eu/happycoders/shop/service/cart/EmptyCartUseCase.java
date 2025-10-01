package eu.happycoders.shop.service.cart;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.model.customer.CustomerId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Use case implementation: Emptying a shopping cart.
 *
 * @author Sven Woltmann
 */
@Service
public class EmptyCartUseCase {

  private final CartRepository cartRepository;

  public EmptyCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Transactional
  public void emptyCart(CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");

    cartRepository.deleteByCustomerId(customerId);
  }
}
