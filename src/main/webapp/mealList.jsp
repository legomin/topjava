<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2>Meal list</h2>

    <table>
        <c:forEach items="${meals}" var="meal">
            <tr style="color: ${meal.isExceed() ? 'red' : 'green'}">
                <td>${meal.getId()}</td>
                <td>${meal.getDateTime()}</td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
                <td><a href="meals?act=delete&id=${meal.getId()}">delete</a> </td>
                <td><a href="meals?act=edit&id=${meal.getId()}">edit</a> </td>
            </tr>
        </c:forEach>
    </table>

    <form method="post">
        <p><input type="hidden" name="idd" value="${editMeal.getId()}"></p>
        <p>date/time: <input type="datetime-local" name="dateTime"  value="${editMeal.getDateTime()}"></p>
        <p>description: <input type="text" name="description"  value="${editMeal.getDescription()}"></p>
        <p>calories: <input type="text" name="calories"  value="${editMeal.getCalories()}"></p>
        <p><input type="submit" value="submit"></p>
    </form>
</body>
</html>
