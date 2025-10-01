package eu.happycoders.shop.persistence.jpa;

import eu.happycoders.shop.persistence.DemoProducts;
import eu.happycoders.shop.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter: Stores products via JPA in a database.
 *
 * @author Sven Woltmann
 */
@Repository
public class JpaProductRepository implements ProductRepository {

  private final JpaProductSpringDataRepository springDataRepository;

  public JpaProductRepository(JpaProductSpringDataRepository springDataRepository) {
    this.springDataRepository = springDataRepository;
  }

  @PostConstruct
  void createDemoProducts() {
    DemoProducts.DEMO_PRODUCTS.forEach(this::save);
  }

  @Override
  public void save(Product product) {
    springDataRepository.save(ProductMapper.toJpaEntity(product));
  }

  @Override
  public Optional<Product> findById(ProductId productId) {
    Optional<ProductJpaEntity> jpaEntity = springDataRepository.findById(productId.value());
    return jpaEntity.map(ProductMapper::toModelEntity);
  }

  @Override
  public List<Product> findByNameOrDescription(String queryString) {
    List<ProductJpaEntity> entities =
        springDataRepository.findByNameOrDescriptionLike("%" + queryString + "%");

    return ProductMapper.toModelEntities(entities);
  }
}
