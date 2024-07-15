<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="style.css">
</head>
<jsp:include page="fragments/headTag.jsp"/>
<jsp:include page="fragments/bodyHeader.jsp"/>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${meal.id == null ? 'Create meal' : 'Edit meal'}</h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form:form action="${pageContext.request.contextPath}/saveMeal" modelAttribute="meal">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form:form>
</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
