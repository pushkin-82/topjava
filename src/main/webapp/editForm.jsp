<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${param.action == "create" ? "Create" : "Edit"} meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == "create" ? "Create" : "Edit"} meal</h2>

    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

    <form method="post" action="meals?action=submit">
        <dl>
            <dd>
                <input type="hidden" name="id" value="${meal.id}"/>
            </dd>
        </dl>
        <dl>
            <dt>Date/Time</dt>
            <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"
                       placeholder="${meal.dateTime.format(formatter)}" /></dd>
        </dl>
        <dl>
            <dt>Description</dt>
            <dd>
                <input type="text" name="description" value="${meal.description}"
                       placeholder="${meal.description}"/>
            </dd>
        </dl>
        <dl>
            <dt>Calories</dt>
            <dd>
                <input type="number" name="calories" value="${meal.calories}" placeholder="${meal.calories}"/>
            </dd>
        </dl>
        <button type="submit">Submit</button>
        <button type="reset">Reset</button>
        <button type="button" onclick="window.history.back()">Back</button>
    </form>
</body>
</html>