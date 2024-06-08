<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .red-text {
            color: red;
        }

        .green-text {
            color: green;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<h2>Meals</h2>
<h3><a href="meals?action=edit">Add meal</a></h3>

<table border="1" rel="">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${meals}" varStatus="loop">
        <tr class="<c:choose>
                        <c:when test="${meal.excess}">red-text</c:when>
                        <c:otherwise>green-text</c:otherwise>
                   </c:choose>">
            <td>${DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${loop.index}">Update</a></td>
            <td><a href="meals?action=delete&id=${loop.index}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
