<%--
  Created by IntelliJ IDEA.
  User: nguyenthiyennhi
  Date: 26/9/25
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh Sách Bài Thi</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
            margin: 0 auto;
            width: 500px;
            margin-bottom: 20px;
            background-color: #e9f5e9;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        input[type="text"] {
            padding: 8px;
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: calc(100% - 22px);
        }
        button {
            margin: 0 auto;
            padding: 8px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #45a049;
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .pagination button {
            margin: 0 5px;
        }
        .active {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
<h1>Danh Sách Bài Thi</h1>
<form:form modelAttribute="exam" action="/exams/add" method="post">
    <form:label path="title">Tiêu đề</form:label>
    <form:input path="title" />
    <form:errors path="title" cssClass="error" />

    <form:label path="description">Mô tả</form:label>
    <form:input path="description" />
    <form:errors path="description" cssClass="error" />

    <button type="submit">Lưu</button>
</form:form>
<table>
    <tr>
        <th>ID</th>
        <th>Tiêu đề</th>
        <th>Mô tả</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="exam" items="${exams}">
        <tr>
            <td>${exam.id}</td>
            <td>${exam.title}</td>
            <td>${exam.description}</td>
            <td>
                <button><a href="/questions/exam/${exam.id}">Xem chi tiết</a></button>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination">
    <c:forEach var="p" items="${pages}">
        <button class="${page == p ? 'active' : ''}">
            <a href="/exams?page=${p - 1}" style="color: white; text-decoration: none;">${p}</a>
        </button>
    </c:forEach>
</div>
</body>
</html>
