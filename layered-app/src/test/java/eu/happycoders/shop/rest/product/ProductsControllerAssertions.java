package eu.happycoders.shop.rest.product;

import eu.happycoders.shop.model.product.Product;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

public final class ProductsControllerAssertions {

  private ProductsControllerAssertions() {}

  public static void assertThatResponseIsProduct(Response response, Product product) {
    assertThat(response.statusCode()).isEqualTo(OK.value());

    JsonPath json = response.jsonPath();

    assertThatJsonProductMatchesProduct(json, true, "", product);
  }

  public static void assertThatResponseIsProductList(Response response, List<Product> products) {
    assertThat(response.statusCode()).isEqualTo(OK.value());

    JsonPath json = response.jsonPath();

    for (int i = 0; i < products.size(); i++) {
      String prefix = "[%d].".formatted(i);
      Product product = products.get(i);
      assertThatJsonProductMatchesProduct(json, false, prefix, product);
    }
  }

  static void assertThatJsonProductMatchesProduct(
      JsonPath json, boolean jsonHasDescription, String prefix, Product product) {
    assertThat(json.getString(prefix + "id")).isEqualTo(product.getId().value());
    assertThat(json.getString(prefix + "name")).isEqualTo(product.getName());

    if (jsonHasDescription) {
      assertThat(json.getString(prefix + "description")).isEqualTo(product.getDescription());
    } else {
      assertThat(json.getString(prefix + "description")).isNull();
    }

    assertThat(json.getString(prefix + "price.currency"))
        .isEqualTo(product.getPrice().currency().getCurrencyCode());
    assertThat(json.getDouble(prefix + "price.amount"))
        .isEqualTo(product.getPrice().amount().doubleValue());

    assertThat(json.getInt(prefix + "itemsInStock")).isEqualTo(product.getItemsInStock());
  }
}
