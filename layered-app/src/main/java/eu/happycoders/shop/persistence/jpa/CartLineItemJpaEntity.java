package eu.happycoders.shop.persistence.jpa;

import jakarta.persistence.*;

/**
 * JPA entity class for a shopping cart line item.
 *
 * @author Sven Woltmann
 */
@Entity
@Table(name = "CartLineItem")
public class CartLineItemJpaEntity {

  @Id @GeneratedValue private Integer id;

  @ManyToOne private CartJpaEntity cart;

  @ManyToOne private ProductJpaEntity product;

  private int quantity;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CartJpaEntity getCart() {
    return cart;
  }

  public void setCart(CartJpaEntity cart) {
    this.cart = cart;
  }

  public ProductJpaEntity getProduct() {
    return product;
  }

  public void setProduct(ProductJpaEntity product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
