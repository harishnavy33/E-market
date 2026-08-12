<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Order Confirmed - HarishMart</title></head>
<body>
    <h1>Order Placed Successfully!</h1>
    <p>Your order ID is <strong>#${orderId}</strong>.</p>
    <p><a href="${pageContext.request.contextPath}/products">Continue Shopping</a></p>
</body>
</html>