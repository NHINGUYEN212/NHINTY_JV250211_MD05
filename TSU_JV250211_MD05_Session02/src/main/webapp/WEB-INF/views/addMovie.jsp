<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 15/9/25
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Thêm Phim Mới</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f4f4f4;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    h1 {
      color: #333;
      margin-bottom: 20px;
    }
    form {
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      width: 400px;
    }
    label {
      display: block;
      margin: 10px 0 5px;
      font-weight: bold;
    }
    input[type="text"],
    input[type="date"],
    input[type="number"] {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 16px;
    }
    input[type="submit"] {
      padding: 10px;
      border: none;
      border-radius: 5px;
      background-color: #4CAF50;
      color: white;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s;
      margin-top: 15px;
    }
    input[type="submit"]:hover {
      background-color: #45a049;
    }
    .error {
      color: red;
      font-size: 20px;
      margin-top: 5px;
    }
  </style>
</head>
<body>
<h1>Thêm Phim Mới</h1>
<c:if test="${not empty message}">
  <script>
    alert("${message}");
  </script>
</c:if>
<form:form action="/movies/add" modelAttribute="movie" method="post">
  <form:label path="title">Tiêu Đề:</form:label>
  <form:input path="title" />
  <form:errors path="title" cssClass="error"/>

  <form:label path="director">Đạo Diễn:</form:label>
  <form:input path="director" />
  <form:errors path="director" cssClass="error"/>

  <form:label path="releaseDate">Ngày Phát Hành:</form:label>
  <form:input path="releaseDate" type="date" />
  <form:errors path="releaseDate" cssClass="error"/>

  <form:label path="rating">Điểm:</form:label>
  <form:input path="rating" type="number" step="0.1"/>
  <form:errors path="rating" cssClass="error"/>

  <input type="submit" value="Thêm Phim"/>
</form:form>
</body>
</html>
