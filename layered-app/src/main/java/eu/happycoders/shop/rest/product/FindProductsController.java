package eu.happycoders.shop.rest.product;

import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.service.product.FindProductsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static eu.happycoders.shop.rest.common.ControllerCommons.clientErrorException;

/**
 * REST controller for all product use cases.
 *
 * @author Sven Woltmann
 */
@RestController
@RequestMapping("/products")
public class FindProductsController {

  private final FindProductsUseCase findProductsUseCase;

  public FindProductsController(FindProductsUseCase findProductsUseCase) {
    this.findProductsUseCase = findProductsUseCase;
  }

  @GetMapping
  public List<ProductInListWebModel> findProducts(
      @RequestParam(value = "query", required = false) String query) {
    if (query == null) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'query'");
    }

    List<Product> products;

    try {
      products = findProductsUseCase.findByNameOrDescription(query);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'query'");
    }

    return products.stream().map(ProductInListWebModel::fromDomainModel).toList();
  }
}
