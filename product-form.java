<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add/Edit Product - HarishMart</title>
</head>

<body>

    <h1>
        ${product != null ? "Edit Product" : "Add Product"}
    </h1>

    <form method="post"
          action="${pageContext.request.contextPath}/products">

        <c:if test="${product != null}">
            <input type="hidden"
                   name="action"
                   value="update"/>

            <input type="hidden"
                   name="id"
                   value="${product.id}"/>
        </c:if>

        <div>
            <label for="name">Name:</label>
            <input type="text"
                   id="name"
                   name="name"
                   value="${product.name}"
                   required>
        </div>

        <br>

        <div>
            <label for="description">Description:</label>
            <textarea id="description"
                      name="description"
                      rows="5"
                      cols="40">${product.description}</textarea>
        </div>

        <br>

        <div>
            <label for="price">Price:</label>
            <input type="number"
                   id="price"
                   name="price"
                   step="0.01"
                   min="0"
                   value="${product.price}"
                   required>
        </div>

        <br>

        <div>
            <label for="stock">Stock:</label>
            <input type="number"
                   id="stock"
                   name="stock"
                   min="0"
                   value="${product.stock}"
                   required>
        </div>

        <br>

        <button type="submit">Save Product</button>

    </form>

</body>
</html>