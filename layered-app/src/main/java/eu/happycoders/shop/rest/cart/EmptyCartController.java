package eu.happycoders.shop.rest.cart;

import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.service.cart.EmptyCartUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static eu.happycoders.shop.rest.common.CustomerIdParser.parseCustomerId;

/**
 * REST controller for all shopping cart use cases.
 *
 * @author Sven Woltmann
 */
@RestController
@RequestMapping("/carts")
public class EmptyCartController {

  private final EmptyCartUseCase emptyCartUseCase;

  public EmptyCartController(EmptyCartUseCase emptyCartUseCase) {
    this.emptyCartUseCase = emptyCartUseCase;
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Void> deleteCart(@PathVariable("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    emptyCartUseCase.emptyCart(customerId);
    return ResponseEntity.noContent().build();
  }
}
