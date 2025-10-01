package eu.happycoders.shop.service.cart;

import eu.happycoders.shop.service.ProductNotFoundException;
import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.persistence.ProductRepository;
import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddToCartUseCaseTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  private final CartRepository cartRepository = mock(CartRepository.class);
  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final AddToCartUseCase addToCartUseCase =
      new AddToCartUseCase(cartRepository, productRepository);

  @BeforeEach
  void initTestDoubles() {
    when(productRepository.findById(TEST_PRODUCT_1.getId())).thenReturn(Optional.of(TEST_PRODUCT_1));

    when(productRepository.findById(TEST_PRODUCT_2.getId())).thenReturn(Optional.of(TEST_PRODUCT_2));
  }

  @Test
  void givenExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
    persistedCart.addProduct(TEST_PRODUCT_1, 1);

    when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(persistedCart));

    Cart cart = addToCartUseCase.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_2.getId(), 3);

    verify(cartRepository).save(cart);

    assertThat(cart.lineItems()).hasSize(2);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(1);
    assertThat(cart.lineItems().get(1).product()).isEqualTo(TEST_PRODUCT_2);
    assertThat(cart.lineItems().get(1).quantity()).isEqualTo(3);
  }

  @Test
  void givenNoExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    Cart cart = addToCartUseCase.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.getId(), 2);

    verify(cartRepository).save(cart);

    assertThat(cart.lineItems()).hasSize(1);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(2);
  }

  @Test
  void givenAnUnknownProductId_addToCart_throwsException() {
    ProductId productId = ProductId.randomProductId();

    ThrowingCallable invocation = () -> addToCartUseCase.addToCart(TEST_CUSTOMER_ID, productId, 1);

    assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(invocation);
    verify(cartRepository, never()).save(any());
  }

  @Test
  void givenQuantityLessThan1_addToCart_throwsException() {
    int quantity = 0;

    ThrowingCallable invocation =
        () -> addToCartUseCase.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.getId(), quantity);

    assertThatIllegalArgumentException().isThrownBy(invocation);
    verify(cartRepository, never()).save(any());
  }
}
