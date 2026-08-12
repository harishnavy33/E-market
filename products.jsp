<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>Products - HarishMart</title></head>
<body>
    <h1>All Products</h1>
    <p><a href="${pageContext.request.contextPath}/products?action=new">+ Add Product (Seller)</a></p>
    <table border="1" cellpadding="6">
        <tr><th>Name</th><th>Description</th><th>Price</th><th>Stock</th></tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>₹<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/></td>
                <td>${product.stock}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html><td>${product.stock}</td>
<td>
    <form method="post" action="${pageContext.request.contextPath}/cart" style="display:inline;">
        <input type="hidden" name="productId" value="${product.id}"/>
        <input type="number" name="quantity" value="1" min="1" style="width:50px;"/>
        <button type="submit">Add to Cart</button>
    </form>
</td>