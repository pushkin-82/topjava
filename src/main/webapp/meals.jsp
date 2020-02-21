<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
    <link href="resources/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="index.html">Home</a> <a href="users.jsp">Users</a></h3>
<hr>
<h2>Meals</h2>

<table class="table">
    <tr class="tableHead">
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <jsp:useBean id="mealList" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
    <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach var="meal" items="${mealList}">
        <tr class="${meal.id % 2 == 0 ? "rowEven" : "rowOdd"}" style="color: ${meal.excess ? "red" : "green"}">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>?</td>
            <td>x</td>
        </tr>

    </c:forEach>
</table>
</body>
</html>
