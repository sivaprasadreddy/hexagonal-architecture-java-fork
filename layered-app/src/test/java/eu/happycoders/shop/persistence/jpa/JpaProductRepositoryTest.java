package eu.happycoders.shop.persistence.jpa;

import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import eu.happycoders.shop.persistence.DemoProducts;
import eu.happycoders.shop.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JpaProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void givenTestProductsAndATestProductId_findById_returnsATestProduct() {
        ProductId productId = DemoProducts.COMPUTER_MONITOR.getId();

        Optional<Product> product = productRepository.findById(productId);

        assertThat(product).contains(DemoProducts.COMPUTER_MONITOR);
    }

    @Test
    void givenTheIdOfAProductNotPersisted_findById_returnsAnEmptyOptional() {
        ProductId productId = new ProductId("00000");

        Optional<Product> product = productRepository.findById(productId);

        assertThat(product).isEmpty();
    }

    @Test
    void
    givenTestProductsAndASearchQueryNotMatchingAndProduct_findByNameOrDescription_returnsAnEmptyList() {
        String query = "not matching any product";

        List<Product> products = productRepository.findByNameOrDescription(query);

        assertThat(products).isEmpty();
    }

    @Test
    void
    givenTestProductsAndASearchQueryMatchingOneProduct_findByNameOrDescription_returnsThatProduct() {
        String query = "lights";

        List<Product> products = productRepository.findByNameOrDescription(query);

        assertThat(products).containsExactlyInAnyOrder(DemoProducts.LED_LIGHTS);
    }

    @Test
    void
    givenTestProductsAndASearchQueryMatchingTwoProducts_findByNameOrDescription_returnsThoseProducts() {
        String query = "monitor";

        List<Product> products = productRepository.findByNameOrDescription(query);

        assertThat(products)
                .containsExactlyInAnyOrder(DemoProducts.COMPUTER_MONITOR, DemoProducts.MONITOR_DESK_MOUNT);
    }
}
