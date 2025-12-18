package com.example.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.store.db.Db;
import com.example.store.model.User;

public class UserDao {

	  public User findByEmail(String email) {
	    String sql = "SELECT id, email, password_hash, full_name FROM users WHERE email = ?";
	    try (Connection c = Db.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql)) {

	      ps.setString(1, email);
	      try (ResultSet rs = ps.executeQuery()) {
	        if (!rs.next()) return null;

	        User u = new User();
	        u.setId(rs.getLong("id"));
	        u.setEmail(rs.getString("email"));
	        u.setPasswordHash(rs.getString("password_hash"));
	        u.setFullName(rs.getString("full_name"));
	        return u;
	      }
	    } catch (SQLException e) {
	      throw new RuntimeException("findByEmail failed", e);
	    }
	  }
	  
	  public long create(String email, String passwordHash, String fullName) {
		  String sql = "INSERT INTO users(email, password_hash, full_name) values (?, ?, ?)";
		  try (Connection c = Db.getConnection();
				  PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			  ps.setString(1, email);
			  ps.setString(2, passwordHash);
			  ps.setString(3,  fullName);
			  ps.executeUpdate();
			  
			  try (ResultSet keys = ps.getGeneratedKeys()) {
				  if (keys.next()) return keys.getLong(1);
				  throw new RuntimeException("No generated key returned");
			  }
		  } catch (SQLException e) {
			  throw new RuntimeException("create user failed: " + e.getMessage(), e);
		  }
		  
		  
	  }
	
}