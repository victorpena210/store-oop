package com.example.store.web;
import java.io.IOException;
import java.sql.Connection;

import com.example.store.db.Db;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/db/ping")
public class DbPingServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain; charset=UTF-8");
		
		try (Connection c = Db.getConnection()) {
			resp.getWriter().println("DB OK ✅");
		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().println("DB FAIL ❌");
			resp.getWriter().println(e.getMessage());
		}
	}
	
}