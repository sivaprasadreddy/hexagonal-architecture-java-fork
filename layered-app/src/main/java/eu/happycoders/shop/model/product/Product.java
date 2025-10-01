package eu.happycoders.shop.model.product;

import eu.happycoders.shop.model.money.Money;

import java.util.Objects;

/**
 * A product listed in the shop.
 *
 * @author Sven Woltmann
 */
public class Product {

  private final ProductId id;
  private String name;
  private String description;
  private Money price;
  private int itemsInStock;

  public Product(ProductId id) {
    this.id = id;
  }

  public Product(ProductId id, String name, String description, Money price, int itemsInStock) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.itemsInStock = itemsInStock;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return itemsInStock == product.itemsInStock && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, itemsInStock);
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", itemsInStock=" + itemsInStock +
            '}';
  }

  public ProductId getId() {
    return id;
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

  public Money getPrice() {
    return price;
  }

  public void setPrice(Money price) {
    this.price = price;
  }

  public int getItemsInStock() {
    return itemsInStock;
  }

  public void setItemsInStock(int itemsInStock) {
    this.itemsInStock = itemsInStock;
  }
}
