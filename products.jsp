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
</html>