<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Thêm Mới Câu Hỏi</title>
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
    form {
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
    }
    input[type="text"] {
      padding: 10px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 4px;
      width: 100%;
      box-sizing: border-box;
    }
    button {
      padding: 10px 15px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
    .error {
      color: red;
      font-size: 0.9em;
    }
  </style>
</head>
<body>
<h1>Thêm Mới Câu Hỏi</h1>
<h2>Bài thi : ${exam.title}</h2>
<form:form modelAttribute="question" action="/questions/exam/${id}/add" method="post">
  <form:label path="content">Nội Dung</form:label>
  <form:input path="content" />
  <form:errors path="content" cssClass="error" />

  <button type="submit">Lưu</button>
</form:form>
</body>
</html>
