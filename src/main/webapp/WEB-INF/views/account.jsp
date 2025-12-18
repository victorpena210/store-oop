<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h1>Your Account</h1>

<p>Logged in as: <%= session.getAttribute("userEmail") %></p>

<form method="post" action="<%= request.getContextPath() %>/logout">
  <button type="submit">Logout</button>
</form>

<p><a href="<%= request.getContextPath() %>/products">Go to products</a></p>
</body></html>
