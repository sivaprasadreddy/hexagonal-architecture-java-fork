package eu.happycoders.shop.persistence.jpa;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;

import java.util.Currency;
import java.util.List;

/**
 * Maps a model product to a JPA product and vice versa.
 *
 * @author Sven Woltmann
 */
final class ProductMapper {

  private ProductMapper() {}

  static ProductJpaEntity toJpaEntity(Product product) {
    ProductJpaEntity jpaEntity = new ProductJpaEntity();

    jpaEntity.setId(product.getId().value());
    jpaEntity.setName(product.getName());
    jpaEntity.setDescription(product.getDescription());
    jpaEntity.setPriceCurrency(product.getPrice().currency().getCurrencyCode());
    jpaEntity.setPriceAmount(product.getPrice().amount());
    jpaEntity.setItemsInStock(product.getItemsInStock());

    return jpaEntity;
  }

  static Product toModelEntity(ProductJpaEntity jpaEntity) {
    return new Product(
        new ProductId(jpaEntity.getId()),
        jpaEntity.getName(),
        jpaEntity.getDescription(),
        new Money(Currency.getInstance(jpaEntity.getPriceCurrency()), jpaEntity.getPriceAmount()),
        jpaEntity.getItemsInStock());
  }

  static List<Product> toModelEntities(List<ProductJpaEntity> jpaEntities) {
    return jpaEntities.stream().map(ProductMapper::toModelEntity).toList();
  }
}
