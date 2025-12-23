package com.example.store.dao;

import com.example.store.db.Db;
import com.example.store.model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

  public List<CartItem> findByUser(long userId) {
    String sql = """
      SELECT c.product_id, c.quantity, p.name, p.category, p.price
      FROM cart_items c
      JOIN products p ON p.id = c.product_id
      WHERE c.user_id = ?
      ORDER BY c.created_at ASC
    """;

    List<CartItem> items = new ArrayList<>();

    try (Connection conn = Db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setLong(1, userId);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          items.add(new CartItem(
              rs.getInt("product_id"),
              rs.getString("name"),
              rs.getString("category"),
              rs.getDouble("price"),
              rs.getInt("quantity")
          ));
        }
      }

      return items;

    } catch (Exception e) {
      throw new RuntimeException("Failed to load cart for user " + userId, e);
    }
  }

  public void addOrIncrement(long userId, int productId, int qtyToAdd) {
    String sql = """
      INSERT INTO cart_items (user_id, product_id, quantity)
      VALUES (?, ?, ?)
      ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
    """;

    try (Connection conn = Db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setLong(1, userId);
      ps.setInt(2, productId);
      ps.setInt(3, qtyToAdd);
      ps.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException("Failed to add to cart", e);
    }
  }

  public void updateQuantity(long userId, int productId, int newQty) {
    if (newQty <= 0) {
      remove(userId, productId);
      return;
    }

    String sql = "UPDATE cart_items SET quantity=? WHERE user_id=? AND product_id=?";

    try (Connection conn = Db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, newQty);
      ps.setLong(2, userId);
      ps.setInt(3, productId);
      ps.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException("Failed to update cart quantity", e);
    }
  }

  public void remove(long userId, int productId) {
    String sql = "DELETE FROM cart_items WHERE user_id=? AND product_id=?";

    try (Connection conn = Db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setLong(1, userId);
      ps.setInt(2, productId);
      ps.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException("Failed to remove from cart", e);
    }
  }

  public void clear(long userId) {
    String sql = "DELETE FROM cart_items WHERE user_id=?";

    try (Connection conn = Db.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setLong(1, userId);
      ps.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException("Failed to clear cart", e);
    }
  }
}
