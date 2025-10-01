package eu.happycoders.shop.service.cart;

import eu.happycoders.shop.service.ProductNotFoundException;
import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.persistence.ProductRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Use case implementation: Adding a product to a shopping cart.
 *
 * @author Sven Woltmann
 */
@Service
public class AddToCartUseCase {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  public AddToCartUseCase(
      CartRepository cartRepository, ProductRepository productRepositoryVeryVeryLong) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepositoryVeryVeryLong;
  }

  @Transactional
  public Cart addToCart(CustomerId customerIdVeryVeryLong, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException {
    Objects.requireNonNull(customerIdVeryVeryLong, "'customerId' must not be null");
    Objects.requireNonNull(productId, "'productId' must not be null");
    if (quantity < 1) {
      throw new IllegalArgumentException("'quantity' must be greater than 0");
    }

    Product product =
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

    Cart cart =
        cartRepository
            .findByCustomerId(customerIdVeryVeryLong)
            .orElseGet(() -> new Cart(customerIdVeryVeryLong));

    cart.addProduct(product, quantity);

    cartRepository.save(cart);

    return cart;
  }
}
