<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Login - HarishMart</title></head>
<body>
    <h1>Login</h1>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Email: <input type="email" name="email" required/></label><br/>
        <label>Password: <input type="password" name="password" required/></label><br/>
        <button type="submit">Login</button>
    </form>
    <p>No account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
</body>
</html>