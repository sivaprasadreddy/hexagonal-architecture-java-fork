package eu.happycoders.shop.persistence.jpa;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartLineItem;
import eu.happycoders.shop.model.cart.NotEnoughItemsInStockException;
import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.persistence.CartRepository;
import eu.happycoders.shop.persistence.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.happycoders.shop.model.money.TestMoneyFactory.euros;
import static eu.happycoders.shop.model.product.TestProductFactory.createTestProduct;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JpaCartRepositoryTest {
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(1, 49));

    private static final AtomicInteger CUSTOMER_ID_SEQUENCE_GENERATOR = new AtomicInteger();

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void initRepositories() {
        persistTestProducts();
    }

    private void persistTestProducts() {
        productRepository.save(TEST_PRODUCT_1);
        productRepository.save(TEST_PRODUCT_2);
    }

    @Test
    void givenACustomerIdForWhichNoCartIsPersisted_findByCustomerId_returnsAnEmptyOptional() {
        CustomerId customerId = createUniqueCustomerId();

        Optional<Cart> cart = cartRepository.findByCustomerId(customerId);

        assertThat(cart).isEmpty();
    }

    @Test
    void givenPersistedCartWithProduct_findByCustomerId_returnsTheAppropriateCart()
            throws NotEnoughItemsInStockException {
        CustomerId customerId = createUniqueCustomerId();

        Cart persistedCart = new Cart(customerId);
        persistedCart.addProduct(TEST_PRODUCT_1, 1);
        cartRepository.save(persistedCart);

        Optional<Cart> cart = cartRepository.findByCustomerId(customerId);

        assertThat(cart).isNotEmpty();
        assertThat(cart.get().getId()).isEqualTo(customerId);
        assertThat(cart.get().lineItems()).hasSize(1);
        assertThat(cart.get().lineItems().getFirst().product()).isEqualTo(TEST_PRODUCT_1);
        assertThat(cart.get().lineItems().getFirst().quantity()).isEqualTo(1);
    }

    @Test
    void
    givenExistingCartWithProduct_andGivenANewCartForTheSameCustomer_saveCart_overwritesTheExistingCart()
            throws NotEnoughItemsInStockException {
        CustomerId customerId = createUniqueCustomerId();

        Cart existingCart = new Cart(customerId);
        existingCart.addProduct(TEST_PRODUCT_1, 1);
        cartRepository.save(existingCart);

        Cart newCart = new Cart(customerId);
        newCart.addProduct(TEST_PRODUCT_2, 2);
        cartRepository.save(newCart);

        Optional<Cart> cart = cartRepository.findByCustomerId(customerId);
        assertThat(cart).isNotEmpty();
        assertThat(cart.get().getId()).isEqualTo(customerId);
        assertThat(cart.get().lineItems()).hasSize(1);
        assertThat(cart.get().lineItems().getFirst().product()).isEqualTo(TEST_PRODUCT_2);
        assertThat(cart.get().lineItems().getFirst().quantity()).isEqualTo(2);
    }

    @Test
    void givenExistingCartWithProduct_addProductAndSaveCart_updatesTheExistingCart()
            throws NotEnoughItemsInStockException {
        CustomerId customerId = createUniqueCustomerId();

        Cart existingCart = new Cart(customerId);
        existingCart.addProduct(TEST_PRODUCT_1, 1);
        cartRepository.save(existingCart);

        existingCart = cartRepository.findByCustomerId(customerId).orElseThrow();
        existingCart.addProduct(TEST_PRODUCT_2, 2);
        cartRepository.save(existingCart);

        Optional<Cart> cart = cartRepository.findByCustomerId(customerId);
        assertThat(cart).isNotEmpty();
        assertThat(cart.get().getId()).isEqualTo(customerId);
        assertThat(cart.get().lineItems())
                .map(CartLineItem::product)
                .containsExactlyInAnyOrder(TEST_PRODUCT_1, TEST_PRODUCT_2);
    }

    @Test
    void givenExistingCart_deleteByCustomerId_deletesTheCart() {
        CustomerId customerId = createUniqueCustomerId();

        Cart existingCart = new Cart(customerId);
        cartRepository.save(existingCart);

        assertThat(cartRepository.findByCustomerId(customerId)).isNotEmpty();

        cartRepository.deleteByCustomerId(customerId);

        assertThat(cartRepository.findByCustomerId(customerId)).isEmpty();
    }

    @Test
    void givenNotExistingCart_deleteByCustomerId_doesNothing() {
        CustomerId customerId = createUniqueCustomerId();
        assertThat(cartRepository.findByCustomerId(customerId)).isEmpty();

        cartRepository.deleteByCustomerId(customerId);

        assertThat(cartRepository.findByCustomerId(customerId)).isEmpty();
    }

    private static CustomerId createUniqueCustomerId() {
        return new CustomerId(CUSTOMER_ID_SEQUENCE_GENERATOR.incrementAndGet());
    }
}
