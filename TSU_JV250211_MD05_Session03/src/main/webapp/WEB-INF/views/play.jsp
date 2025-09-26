<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Document</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }

    h3 {
      color: #333;
    }

    form {
      background: white;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      max-width: 500px;
      margin: auto;
    }

    label {
      display: block;
      margin: 10px 0;
      color: #555;
    }

    input[type="radio"] {
      margin-right: 10px;
    }

    button {
      background-color: #5cb85c;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    button:hover {
      background-color: #4cae4c;
    }

    .alert {
      color: #ff0000;
      margin-top: 15px;
      text-align: center;
    }
  </style>
</head>
<body>
<form action="/home/play/exam/${examId}" method="post">
  <h3>Câu hỏi : ${question.content}</h3>
  <c:forEach var="answer" items="${answers}">
    <label for="${answer.id}">
      <input type="radio" value="${answer.correct}" name="answer" id="${answer.id}">
        ${answer.content}
    </label>
  </c:forEach>
  <button type="submit">Submit</button>
</form>

<c:if test="${not empty message}">
  <div class="alert">
    <script>
      alert("${message}")
    </script>
  </div>
</c:if>
</body>
</html>
