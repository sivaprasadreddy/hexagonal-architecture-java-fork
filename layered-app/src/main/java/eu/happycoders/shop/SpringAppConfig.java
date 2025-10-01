package eu.happycoders.shop;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.persistence.ProductRepository;
import eu.happycoders.shop.service.cart.AddToCartUseCase;
import eu.happycoders.shop.service.cart.EmptyCartUseCase;
import eu.happycoders.shop.service.cart.GetCartUseCase;
import eu.happycoders.shop.service.product.FindProductsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring application configuration, making Spring beans from services defined in application
 * module.
 *
 * @author Sven Woltmann
 */
@SpringBootApplication
public class SpringAppConfig {

  @Autowired CartRepository cartRepository;

  @Autowired ProductRepository productRepository;

  @Bean
  GetCartUseCase getCartUseCase() {
    return new GetCartUseCase(cartRepository);
  }

  @Bean
  EmptyCartUseCase emptyCartUseCase() {
    return new EmptyCartUseCase(cartRepository);
  }

  @Bean
  FindProductsUseCase findProductsUseCase() {
    return new FindProductsUseCase(productRepository);
  }

  @Bean
  AddToCartUseCase addToCartUseCase() {
    return new AddToCartUseCase(cartRepository, productRepository);
  }
}
