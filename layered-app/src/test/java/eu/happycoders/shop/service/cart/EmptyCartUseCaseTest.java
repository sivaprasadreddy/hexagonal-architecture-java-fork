package eu.happycoders.shop.service.cart;

import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.model.customer.CustomerId;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmptyCartUseCaseTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);

  private final CartRepository cartRepository = mock(CartRepository.class);
  private final EmptyCartUseCase emptyCartUseCase = new EmptyCartUseCase(cartRepository);

  @Test
  void emptyCart_invokesDeleteOnThePersistencePort() {
    emptyCartUseCase.emptyCart(TEST_CUSTOMER_ID);

    verify(cartRepository).deleteByCustomerId(TEST_CUSTOMER_ID);
  }
}
