<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Add/Edit Product - HarishMart</title></head>
<body>
    <h1>${product != null ? "Edit Product" : "Add Product"}</h1>
    <form method="post" action="${pageContext.request.contextPath}/products">
        <c:if test="${product != null}">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="id" value="${product.id}"/>
        </c:if>
        <label>Name: <input type="text" name="name" value="${product.name}" required/></label><br/>
        <label>Description: <textarea name="description">${product.description}</textarea></label><br/>
        <label>Price: <input type="number" step="0.01" name="price" value="${product.price}" required/></label><br/>
        <label>Stock: <input type="number" name="stock" value="${product.stock}" required/></label><br/>
        <button type="submit">Save</button>
    </form>
</body>
</html>