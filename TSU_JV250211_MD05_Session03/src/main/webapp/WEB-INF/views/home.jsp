<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Danh Sách Bài Thi</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f4f4f4;
    }
    h1 {
      text-align: center;
      color: #333;
    }
    .exam-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
    }
    .exam-card {
      background-color: #fff;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      margin: 10px;
      padding: 15px;
      width: calc(25% - 20px); /* 4 bài thi mỗi hàng */
      box-sizing: border-box;
    }
    .exam-title {
      color: #007BFF;
    }
    .pagination {
      display: flex;
      justify-content: center;
      margin-top: 20px;
    }
    .pagination a {
      margin: 0 5px;
      padding: 8px 12px;
      border: 1px solid #007BFF;
      border-radius: 5px;
      text-decoration: none;
      color: #007BFF;
    }
    .pagination a:hover {
      background-color: #007BFF;
      color: white;
    }
  </style>
</head>
<body>
<h1>Danh sách bài thi</h1>
<form action="/home" method="get">
  <input type="text" name="search" value="${search}" placeholder="search by title">
  <button type="submit">Search</button>
</form>
<c:if test="${not empty message}">
  <script>
    alert("${message}")
  </script>
</c:if>
<div class="exam-container">
  <c:forEach var="exam" items="${exams}">
    <div class="exam-card">
      <h3 class="exam-title">${exam.title}</h3>
      <p>${exam.description}</p>
      <button><a href="/home/play/${exam.id}">Play</a></button>
    </div>
  </c:forEach>
</div>

<div class="pagination">
  <c:if test="${page.number > 0}">
    <a href="?page=${page.number - 1}&size=${page.size}&search=${param.search}">Previous</a>
  </c:if>
  <c:forEach var="i" items="${totalPage}">
    <a href="?page=${i - 1}&size=${page.size}&search=${param.search}">${i}</a>
  </c:forEach>
  <c:if test="${page.number < totalPage.size()}">
    <a href="?page=${page.number + 1}&size=${page.size}&search=${param.search}">Next</a>
  </c:if>
</div>

</body>
</html>
