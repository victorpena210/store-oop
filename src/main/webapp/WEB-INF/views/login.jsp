<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h1>Login</h1>

<% if (request.getAttribute("error") != null) { %>
  <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

<form method="post" action="<%= request.getContextPath() %>/login">
  <div>Email: <input name="email" type="email" /></div>
  <div>Password: <input name="password" type="password" /></div>
  <button type="submit">Login</button>
</form>

<p><a href="<%= request.getContextPath() %>/register">Need an account? Register</a></p>
</body></html>
