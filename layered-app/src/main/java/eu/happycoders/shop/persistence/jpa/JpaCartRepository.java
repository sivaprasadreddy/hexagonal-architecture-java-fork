package eu.happycoders.shop.persistence.jpa;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Persistence adapter: Stores carts via JPA in a database.
 *
 * @author Sven Woltmann
 */
@Repository
public class JpaCartRepository implements CartRepository {

  private final JpaCartSpringDataRepository springDataRepository;

  public JpaCartRepository(JpaCartSpringDataRepository springDataRepository) {
    this.springDataRepository = springDataRepository;
  }

  @Override
  public void save(Cart cart) {
    springDataRepository.save(CartMapper.toJpaEntity(cart));
  }

  @Override
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    Optional<CartJpaEntity> cartJpaEntity = springDataRepository.findByIdWithLineItems(customerId.value());
    return cartJpaEntity.map(CartMapper::toModelEntity);
  }

  @Override
  public void deleteByCustomerId(CustomerId customerId) {
    springDataRepository.deleteById(customerId.value());
  }
}
