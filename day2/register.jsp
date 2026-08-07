<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Register - HarishMart</title></head>
<body>
    <h1>Create an Account</h1>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>Name: <input type="text" name="name" required/></label><br/>
        <label>Email: <input type="email" name="email" required/></label><br/>
        <label>Password: <input type="password" name="password" required/></label><br/>
        <label>Role:
            <select name="role">
                <option value="BUYER">Buyer</option>
                <option value="SELLER">Seller</option>
            </select>
        </label><br/>
        <button type="submit">Register</button>
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
</body>
</html>