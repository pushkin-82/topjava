<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2><a href="meals?action=create">Add new meal</a></h2>

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
                <td><a href="meals?action=update&id=${meal.id}">edit</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">x</a></td>
            </tr>

        </c:forEach>
    </table>
</body>
</html>
