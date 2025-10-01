package eu.happycoders.shop.rest.cart;

import eu.happycoders.shop.service.ProductNotFoundException;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.ProductId;
import eu.happycoders.shop.service.cart.AddToCartUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static eu.happycoders.shop.rest.common.ControllerCommons.clientErrorException;
import static eu.happycoders.shop.rest.common.CustomerIdParser.parseCustomerId;
import static eu.happycoders.shop.rest.common.ProductIdParser.parseProductId;

/**
 * REST controller for all shopping cart use cases.
 *
 * @author Sven Woltmann
 */
@RestController
@RequestMapping("/carts")
public class AddToCartController {

  private final AddToCartUseCase addToCartUseCase;

  public AddToCartController(AddToCartUseCase addToCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
  }

  @PostMapping("/{customerId}/line-items")
  public CartWebModel addLineItem(
      @PathVariable("customerId") String customerIdString,
      @RequestParam("productId") String productIdString,
      @RequestParam("quantity") int quantity) {
    CustomerId customerId = parseCustomerId(customerIdString);
    ProductId productId = parseProductId(productIdString);

    try {
      Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartWebModel.fromDomainModel(cart);
    } catch (ProductNotFoundException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "The requested product does not exist");
    } catch (NotEnoughItemsInStockException e) {
      throw clientErrorException(
          HttpStatus.BAD_REQUEST, "Only %d items in stock".formatted(e.itemsInStock()));
    }
  }
}
