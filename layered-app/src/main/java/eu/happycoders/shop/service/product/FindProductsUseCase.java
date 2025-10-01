package eu.happycoders.shop.service.product;

import eu.happycoders.shop.persistence.ProductRepository;
import eu.happycoders.shop.model.product.Product;

import java.util.List;
import java.util.Objects;

/**
 * Use case implementation: Finding products via a search query.
 *
 * @author Sven Woltmann
 */
public class FindProductsUseCase {

  private final ProductRepository productRepository;

  public FindProductsUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> findByNameOrDescription(String query) {
    Objects.requireNonNull(query, "'query' must not be null");
    if (query.length() < 2) {
      throw new IllegalArgumentException("'query' must be at least two characters long");
    }

    return productRepository.findByNameOrDescription(query);
  }
}
