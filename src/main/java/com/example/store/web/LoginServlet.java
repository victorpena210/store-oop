package com.example.store.web;

import com.example.store.dao.UserDao;
import com.example.store.model.User;
import com.example.store.security.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private final UserDao userDao = new UserDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String email = req.getParameter("email");
    String password = req.getParameter("password");

    User u = (email == null) ? null : userDao.findByEmail(email);
    if (u == null || !PasswordUtil.verify(password == null ? "" : password, u.getPasswordHash())) {
      req.setAttribute("error", "Invalid email or password.");
      req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
      return;
    }

    HttpSession session = req.getSession(true);
    session.setAttribute("userId", u.getId());
    session.setAttribute("userEmail", u.getEmail());

    resp.sendRedirect(req.getContextPath() + "/account");
  }
}
