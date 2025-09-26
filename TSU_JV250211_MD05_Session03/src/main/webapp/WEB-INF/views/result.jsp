<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Document</title>
</head>
<body>
<c:if test="${not empty message}">
  <script>
    alert("${message}")
  </script>
</c:if>
<h2>Xin chúc mừng bạn đã vượt qua bài thi</h2>
<h3>Bạn đã trả lời đúng ${trueAnswer}/${totalQuestion} câu hỏi</h3>
<h3>Và trả lời sai ${falseAnswer}/${totalQuestion} câu hỏi</h3>
<button><a href="/home">Quay la trang chủ</a></button>
</body>
</html>
