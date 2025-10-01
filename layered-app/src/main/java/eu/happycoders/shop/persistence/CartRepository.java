package eu.happycoders.shop.persistence;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;

import java.util.Optional;

/**
 * Outgoing persistence port for carts.
 *
 * @author Sven Woltmann
 */
public interface CartRepository {

  void save(Cart cart);

  Optional<Cart> findByCustomerId(CustomerId customerId);

  void deleteByCustomerId(CustomerId customerId);
}
