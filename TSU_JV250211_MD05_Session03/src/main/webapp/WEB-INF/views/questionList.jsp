<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Danh Sách Câu Hỏi</title>

  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }
    h1 {
      color: #333;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      padding: 10px;
      border: 1px solid #ddd;
      text-align: left;
    }
    th {
      background-color: #4CAF50;
      color: white;
    }
    form {
      margin-bottom: 20px;
    }
    input[type="text"] {
      padding: 8px;
      margin-right: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    button {
      padding: 8px 12px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<h1>Danh Sách Câu Hỏi</h1>
<h2>Bài thi : ${exam.title}</h2>
<button><a href="/questions/exam/${examId}/add">Thêm mới câu hỏi</a></button>
<table>
  <tr>
    <th>ID</th>
    <th>Nội Dung</th>
    <th>Hành Động</th>
  </tr>
  <c:forEach var="question" items="${questions}">
    <tr>
      <td>${question.id}</td>
      <td>${question.content}</td>
      <td>
        <button><a href="/answers/questions/${question.id}">Xem chi tiết</a></button>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
