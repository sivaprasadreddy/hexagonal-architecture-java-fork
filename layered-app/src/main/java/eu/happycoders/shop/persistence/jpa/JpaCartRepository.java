package eu.happycoders.shop.persistence.jpa;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.customer.CustomerId;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
  @Transactional
  public void save(Cart cart) {
    springDataRepository.save(CartMapper.toJpaEntity(cart));
  }

  @Override
  @Transactional
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    Optional<CartJpaEntity> cartJpaEntity = springDataRepository.findById(customerId.value());
    return cartJpaEntity.map(CartMapper::toModelEntity);
  }

  @Override
  @Transactional
  public void deleteByCustomerId(CustomerId customerId) {
    springDataRepository.deleteById(customerId.value());
  }
}
