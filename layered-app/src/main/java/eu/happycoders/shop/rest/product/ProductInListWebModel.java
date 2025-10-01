package eu.happycoders.shop.rest.product;

import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

/**
 * Model class for returning a product (in a list ... that's without description) via REST API.
 *
 * @author Sven Woltmann
 */
public record ProductInListWebModel(String id, String name, Money price, int itemsInStock) {

  public static ProductInListWebModel fromDomainModel(Product product) {
    return new ProductInListWebModel(
        product.getId().value(), product.getName(), product.getPrice(), product.getItemsInStock());
  }
}
