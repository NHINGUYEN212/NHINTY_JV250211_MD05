<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Chi Tiết Câu Hỏi</title>
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
    h2 {
      color: #555;
    }
    form {
      margin-bottom: 20px;
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      width: 400px; /* Đặt chiều rộng của form */
    }
    input[type="text"], select {
      margin: 10px 0;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      width: calc(100% - 22px); /* Giữ chiều rộng cho input và select */
      box-sizing: border-box;
    }
    button {
      padding: 10px 15px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.3s;
      width: 100%; /* Nút sẽ chiếm toàn bộ chiều rộng của form */
    }
    button:hover {
      background-color: #45a049;
    }
    .error {
      color: red;
      font-size: 0.9em;
      margin-top: 5px;
    }
  </style>
</head>
<body>
<h1>Chi Tiết Câu Hỏi</h1>
<h2>${question.content}</h2>
<p>Nội dung: ${question.content}</p>

<h2>Danh Sách Đáp Án</h2>
<div class="answer-list">
  <c:forEach var="answer" items="${answers}">
    <div class="answer-item">
      <p>${answer.content} <strong>${answer.correct ? '(Đáp án đúng)' : ''}</strong></p>
    </div>
  </c:forEach>
</div>
<form:form modelAttribute="answerDTO" action="/answers/question/${id}/add" method="post">
  <h2 style="text-align: center">Thêm đáp án</h2>
  <form:label path="content">Nội Dung</form:label>
  <form:input path="content" />
  <form:errors path="content" cssClass="error" />

  <form:label path="correct">Đáp Án Đúng?</form:label>
  <form:select path="correct">
    <form:option value="true">Đúng</form:option>
    <form:option value="false">Sai</form:option>
  </form:select>
  <form:errors path="correct" cssClass="error" />

  <button type="submit">Thêm</button>
</form:form>

</body>
</html>
