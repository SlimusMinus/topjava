<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<h3>${meal.dateTime==null? "Add meal" : "Edit meal"}</h3>
<form method="post" action="meals">
    <div>
        <label for="date">DateTime </label>
        <input type="datetime-local" id="date" name="date" value="${meal.dateTime}">
        <br><br>
    </div>
    <div>
        <label>
            <label for="description">Description </label>
            <input type="text" id="description" name="description" value="${meal.description}">
        </label>
        <br><br>
    </div>
    <div>
        <label>
            <label for="calories">Calories </label>
            <input type="text" id="calories" name="calories" value="${meal.calories}">
        </label>
        <br><br>
    </div>
    <input type="submit" value="Save">
    <input type="button" value="Cancel" onclick="window.history.back()">
</form>
</body>
</html>