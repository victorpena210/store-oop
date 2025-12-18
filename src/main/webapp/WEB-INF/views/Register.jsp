<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h1>Register</h1>

<% if (request.getAttribute("error") != null) { %>
  <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

<form method="post" action="<%= request.getContextPath() %>/register">
  <div>Full name: <input name="fullName" /></div>
  <div>Email: <input name="email" type="email" /></div>
  <div>Password: <input name="password" type="password" /></div>
  <button type="submit">Create account</button>
</form>

<p><a href="<%= request.getContextPath() %>/login">Already have an account? Login</a></p>
</body></html>
