<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Products</title></head>
<body>
<h1>Products</h1>

<table border="1" cellpadding="8">
  <tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th></tr>

  <%
    java.util.List<com.example.store.model.Product> products =
        (java.util.List<com.example.store.model.Product>) request.getAttribute("products");
    for (com.example.store.model.Product p : products) {
  %>
    <tr>
      <td><%= p.getId() %></td>
      <td><%= p.getName() %></td>
      <td><%= p.getCategory() %></td>
      <td>$<%= p.getPrice() %></td>
    </tr>
  <%
    }
  %>
</table>

</body>
</html>
