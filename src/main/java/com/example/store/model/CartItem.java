package com.example.store.model;

public class CartItem {
  private int productId;
  private String name;
  private String category;
  private double price;
  private int quantity;

  public CartItem() {}

  public CartItem(int productId, String name, String category, double price, int quantity) {
    this.productId = productId;
    this.name = name;
    this.category = category;
    this.price = price;
    this.quantity = quantity;
  }

  public int getProductId() { return productId; }
  public void setProductId(int productId) { this.productId = productId; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  public int getQuantity() { return quantity; }
  public void setQuantity(int quantity) { this.quantity = quantity; }

  public double getLineTotal() { return price * quantity; }
}
