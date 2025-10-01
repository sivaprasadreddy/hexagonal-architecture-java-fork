package eu.happycoders.shop.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * JPA entity class for a product.
 *
 * @author Sven Woltmann
 */
@Entity
@Table(name = "Product")
public class ProductJpaEntity {

  @Id private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String priceCurrency;

  @Column(nullable = false)
  private BigDecimal priceAmount;

  private int itemsInStock;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPriceCurrency() {
    return priceCurrency;
  }

  public void setPriceCurrency(String priceCurrency) {
    this.priceCurrency = priceCurrency;
  }

  public BigDecimal getPriceAmount() {
    return priceAmount;
  }

  public void setPriceAmount(BigDecimal priceAmount) {
    this.priceAmount = priceAmount;
  }

  public int getItemsInStock() {
    return itemsInStock;
  }

  public void setItemsInStock(int itemsInStock) {
    this.itemsInStock = itemsInStock;
  }
}
