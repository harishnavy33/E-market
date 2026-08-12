<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>Your Cart - HarishMart</title></head>
<body>
    <h1>Your Cart</h1>
    <c:if test="${empty cartItems}">
        <p>Your cart is empty. <a href="${pageContext.request.contextPath}/products">Browse products</a></p>
    </c:if>
    <c:if test="${not empty cartItems}">
        <table border="1" cellpadding="6">
            <tr><th>Product</th><th>Price</th><th>Qty</th><th>Line Total</th><th></th></tr>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>${item.productName}</td>
                    <td>₹<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/></td>
                    <td>${item.quantity}</td>
                    <td>₹<fmt:formatNumber value="${item.lineTotal}" pattern="#,##0.00"/></td>
                    <td><a href="${pageContext.request.contextPath}/cart?action=remove&id=${item.id}">Remove</a></td>
                </tr>
            </c:forEach>
        </table>
        <p><a href="${pageContext.request.contextPath}/checkout">Proceed to Checkout</a></p>
    </c:if>
</body>
</html>