<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 15/9/25
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Danh Sách Sách</title>
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
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    table th, table td {
      border: 1px solid #ddd;
      padding: 8px;
    }
    table th {
      background-color: #4CAF50;
      color: white;
    }
    .search-form {
      display: flex;
      justify-content: center;
      margin-bottom: 20px;
    }
    .search-form input[type="text"] {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    .btn {
      display: inline-block;
      margin: 10px 0;
      padding: 10px 15px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      text-align: center;
    }
    .btn:hover {
      background-color: #45a049;
    }



  </style>
</head>
<body>
<c:if test="${not empty message}">
  <script>
    alert("${message}")
  </script>
</c:if>
<div class="container">
  <h1>Danh Sách Sách</h1>
  <form action="/books" method="get" class="search-form">
    <input type="text" name="search" value="${search}" placeholder="Tìm kiếm sách..." required />
    <input type="submit" value="Tìm" />
  </form>
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Tiêu Đề</th>
      <th>Tác Giả</th>
      <th>Giá</th>
      <th>Hành Động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${books}" var="book">
      <tr>
        <td>${book.id}</td>
        <td>${book.title}</td>
        <td>${book.author}</td>
        <td>${book.price}</td>
        <td>
          <a href="/books/edit/${book.id}">Sửa</a>
          <a href="/books/delete/${book.id}" onclick="return confirm('bạn có chắc chắn muốn xóa sách này không ?')">Xóa</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <a href="/books/add" class="btn">Thêm Sách</a>


</div>
</body>
</html>
