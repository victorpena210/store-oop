package com.example.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.store.db.Db;
import com.example.store.model.Product;

public class ProductDao {
	
	public List<Product> findAll() {
		
		String sql = "SELECT id, name, category, price FROM products ORDER BY";
		List<Product> list = new ArrayList<>();
		
		try (Connection conn = Db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			
			while(rs.next()) {
				list.add(new Product(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("category"),
						rs.getDouble("price")
						));
				}
			return list;
			} catch (Exception e) {
				throw new RuntimeException("failed to load products", e);
		}
	}

}