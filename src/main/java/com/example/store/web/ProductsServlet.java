package com.example.store.web;

import java.io.IOException;

import com.example.store.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
  private final ProductDao productDao = new ProductDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setAttribute("products", productDao.findAll());
    req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
  }
}
