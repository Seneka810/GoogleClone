<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GoogleClone</title>
</head>
<body>
<div align="center">
<c:url value="/index" var="newlink" />
<form action="${newlink}" method="POST">
    Enter link:<br /> <input type="text" name="link"><br /><br />
    <input type="submit" value="Index" />
</form>
</body>
</html>
