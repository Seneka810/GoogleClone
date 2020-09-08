<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GoogleClone</title>
</head>
<body>
<div align="center">
    <c:url value="/search" var="viewRes" />
    <form action="${viewRes}" method="POST">
        Enter key word:<br /> <input type="text" name="keyWord"><br /><br />
        <input type="submit" value="Search" />
    </form>
</body>
</html>
