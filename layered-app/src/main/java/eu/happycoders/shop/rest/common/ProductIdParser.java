package eu.happycoders.shop.rest.common;

import eu.happycoders.shop.model.product.ProductId;
import org.springframework.http.HttpStatus;

import static eu.happycoders.shop.rest.common.ControllerCommons.clientErrorException;

/**
 * A parser for product IDs, throwing a {@link
 * org.springframework.web.client.HttpClientErrorException} for invalid product IDs.
 *
 * @author Sven Woltmann
 */
public final class ProductIdParser {

  private ProductIdParser() {}

  public static ProductId parseProductId(String string) {
    if (string == null) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'productId'");
    }

    try {
      return new ProductId(string);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'productId'");
    }
  }
}
