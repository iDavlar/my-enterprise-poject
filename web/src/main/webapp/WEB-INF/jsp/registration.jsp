<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" \>
    <title>Регистрация</title>
</head>
<body>
<h2>Регистрация</h2>
<form name="registration-form" method="post" action="registration">
    <label for="firstName">Имя:</label><br>
    <input type="text" id="firstName" name="firstName" placeholder="Введите имя" value="${after.FirstName}">
    <br>

    <label for="lastName">Фамилия:</label><br>
    <input type="text" id="lastName" name="lastName" placeholder="Введите фамилию" value="${after.LastName}">
    <br>

    <label for="birthday">Дата рождения:</label><br>
    <input type="date" id="birthday" name="birthday" placeholder="Укажите дату рождения" value="${after.Birthday}">
    <br>

    <label for="telephone">Телефон:</label><br>
    <input type="text" id="telephone" name="telephone" placeholder="+7 999 999 99 99" value="${after.Telephone}">
    <br>

    <label for="login">Логин:</label><br>
    <input type="text" id="login" name="login" placeholder="Придумайте логин" value="${after.Login}">
    <br>

    <label for="password">Пароль:</label><br>
    <input type="password" id="password" name="password" placeholder="Придумайте пароль" value="${after.Password}">
    <br>

    <label for="role">Роль:</label><br>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}">${role}</option>
            <br>
        </c:forEach>
    </select>
    <br>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
    <br>
    <input type="submit" value="Завершить">
</form>

<p><a href="index.jsp">Войти</a></p>
</body>
</html>

