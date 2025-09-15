<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 15/9/25
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Danh Sách Phim</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f4f4f4;
    }
    h1 {
      color: #333;
      text-align: center;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin: 20px 0;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
    }
    th {
      background-color: #4CAF50;
      color: white;
    }
    form {
      margin: 20px auto;
      text-align: center;
    }
    input[type="text"] {
      padding: 10px;
      width: 300px;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin-right: 10px;
      font-size: 16px;
    }
    input[type="submit"] {
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      background-color: #4CAF50;
      color: white;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
    .pagination {
      text-align: center;
      margin: 20px 0;
    }
    .pagination a {
      margin: 0 5px;
      padding: 8px 12px;
      text-decoration: none;
      border: 1px solid #ddd;
      border-radius: 5px;
      color: #333;
      transition: background-color 0.3s;
    }
    .pagination a:hover {
      background-color: #4CAF50;
      color: white;
    }
    .add-movie-btn {
      display: block;
      margin: 20px auto;
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      background-color: #007BFF;
      color: white;
      font-size: 16px;
      text-align: center;
      text-decoration: none;
      transition: background-color 0.3s;
    }
    .add-movie-btn:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<h1>Danh Sách Phim</h1>
<c:if test="${not empty message}">
  <script>
    alert("${message}");
  </script>
</c:if>

<a href="/movies/add" class="add-movie-btn">Thêm Mới Phim</a>

<form action="searchMovies" method="get">
  <input type="text" name="keyword" placeholder="Tìm kiếm phim hoặc đạo diễn" required>
  <input type="submit" value="Tìm Kiếm">
</form>
<table>
  <tr>
    <th>Tiêu Đề</th>
    <th>Đạo Diễn</th>
    <th>Ngày Phát Hành</th>
    <th>Điểm</th>
    <th>Hành Động</th>
  </tr>
  <c:forEach var="movie" items="${movies}">
    <tr>
      <td>${movie.title}</td>
      <td>${movie.director}</td>
      <td>${movie.releaseDate}</td>
      <td>${movie.rating}</td>
      <td>
        <button><a href="/movies/delete/${movie.id}" onclick="return confirm('Bạn có chắc chắn muốn xóa phim này không ?')">Xóa</a></button>
      </td>
    </tr>
  </c:forEach>
</table>
<div class="pagination">
  <c:forEach var="i" items="${totalPage}">
    <a href="/movies?page=${i}" >${i}</a>
  </c:forEach>
</div>
</body>
</html>
