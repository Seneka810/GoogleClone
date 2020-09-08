<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GoogleClone</title>
</head>
<body>
<c:forEach items="${stringsHtml}" var="stringHtml">
    <c:out value="${stringHtml}"/><br />
</c:forEach>
</body>
</html>
