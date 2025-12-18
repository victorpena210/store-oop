package com.example.store.web;

import java.io.IOException;

import com.example.store.dao.UserDao;
import com.example.store.security.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private final UserDao userDao = new UserDao();
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String fullName = req.getParameter("fullName");
		String password = req.getParameter("password");
		
		if (email == null || email.isBlank() || password == null || password.isBlank() || fullName == null || fullName.isBlank()) {
			req.setAttribute("error", "All fields are required");
			req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
			return;
		}
		
		if (userDao.findByEmail(email)!= null) {
			req.setAttribute("error", "Email already registerd.");
			req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
			return;
		}
		
		String hash = PasswordUtil.hash(password);
		long userId = userDao.create(email, hash, fullName);
		
		//start session (auto-login after register)
		HttpSession session = req.getSession(true);
		session.setAttribute("userId", userId);
		session.setAttribute("userEmail", email);
		
		resp.sendRedirect(req.getContextPath() + "/account");
		
		
	}
	
}