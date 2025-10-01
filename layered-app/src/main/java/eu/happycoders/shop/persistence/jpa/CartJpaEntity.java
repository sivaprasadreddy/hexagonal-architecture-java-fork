package eu.happycoders.shop.persistence.jpa;

import jakarta.persistence.*;

import java.util.List;

/**
 * JPA entity class for a shopping cart.
 *
 * @author Sven Woltmann
 */
@Entity
@Table(name = "Cart")
public class CartJpaEntity {

  @Id private int customerId;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartLineItemJpaEntity> lineItems;

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public List<CartLineItemJpaEntity> getLineItems() {
    return lineItems;
  }

  public void setLineItems(List<CartLineItemJpaEntity> lineItems) {
    this.lineItems = lineItems;
  }
}
