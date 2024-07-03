<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" \>
    <title>Вход</title>
</head>
<body>
<h2>Регистрация</h2>
<form name="login-form" method="post" action="${pageContext.request.contextPath}/login">
    <label for="login">Логин:</label><br>
    <input type="text" id="login" name="login" placeholder="Введите логин" value="${param.login}" required>
    <br>

    <label for="password">Пароль:</label><br>
    <input type="password" id="password" name="password" placeholder="Введите пароль" required>
    <br>

    <c:if test="${param.error != null}">
        <div style="color: red">
            <span>Неверный логин или пароль</span>
            <br>
        </div>
    </c:if>
    <br>
    <input type="submit" value="Войти">
</form>

<p><a href="${pageContext.request.contextPath}/registration">Регистрация</a></p>
</body>
</html>

