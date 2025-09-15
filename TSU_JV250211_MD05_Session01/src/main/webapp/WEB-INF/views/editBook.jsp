<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 15/9/25
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Sửa Sách</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }
    .container {
      max-width: 800px;
      margin: auto;
      background: white;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h1 {
      text-align: center;
    }
    form {
      display: flex;
      flex-direction: column;
    }
    label {
      margin: 10px 0 5px;
    }
    input[type="text"] {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    input[type="submit"] {
      margin-top: 20px;
      padding: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>

<div class="container">

  <h1>Sửa Thông Tin Sách</h1>
  <form action="/books/edit/${book.id}" method="post">
    <c:if test="${not empty message}">
      <div style="color: red; text-align: center;">
          ${message}
      </div>
    </c:if>
    <input type="hidden" name="id" value="${book.id}">
    <label for="title">Tiêu Đề:</label>
    <input type="text" name="title" value="${book.title}" id="title"  required /><br/>
    <label for="author">Tác Giả:</label>
    <input type="text" name="author" value="${book.author}" id="author" required /><br/>
    <label for="price">Giá:</label>
    <input type="text" name="price" id="price" value="${book.price}" required /><br/>
    <input type="submit" value="Cập Nhật" />
  </form>
  <a href="/books" class="btn">Quay Lại</a>
</div>
</body>
</html>
