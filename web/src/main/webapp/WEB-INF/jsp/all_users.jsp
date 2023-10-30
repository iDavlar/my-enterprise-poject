<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<%@include file="header.jsp" %>
<c:if test="${not empty requestScope.usersList}">
    <c:forEach var="user" items="${requestScope.usersList}">
        <h3>${user.getFirstName()}</h3>
    </c:forEach>
</c:if>
<h1>${requestScope.usersList.size()}</h1>

</body>
</html>
