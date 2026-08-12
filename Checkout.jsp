<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head><title>Checkout - HarishMart</title></head>
<body>
    <h1>Order Summary</h1>
    <table border="1" cellpadding="6">
        <tr><th>Product</th><th>Qty</th><th>Line Total</th></tr>
        <c:forEach var="item" items="${cartItems}">
            <tr>
                <td>${item.productName}</td>
                <td>${item.quantity}</td>
                <td>₹<fmt:formatNumber value="${item.lineTotal}" pattern="#,##0.00"/></td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" action="${pageContext.request.contextPath}/checkout">
        <button type="submit">Place Order</button>
    </form>
</body>
</html>