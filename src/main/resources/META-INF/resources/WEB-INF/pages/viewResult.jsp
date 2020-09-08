<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GoogleClone</title>
</head>
<body>
<c:forEach items="${doc}" var="d">
    <a href="${d.value}"><c:out value="${d.key}"/></a><br />
</c:forEach>

<a href="${url}">
    <c:out value="${url}" />
</a>
</body>
</html>
