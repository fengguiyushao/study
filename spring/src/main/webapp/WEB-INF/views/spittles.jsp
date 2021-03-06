<%--
  Created by IntelliJ IDEA.
  User: robinzhou
  Date: 2016/8/31
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<html>
<head>
    <title>Spittr</title>
    <%--<link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />">--%>
</head>
<body>
<h1>Recent Spittles</h1>
<c:forEach items="${spittleList}" var="spittle">
    <li id="spittle_<c:out value="spittle.id"/>">
        <div class="spittleMessage">
            <c:out value="${spittle.message}"/>
        </div>
        <div>
            <span class="spittleTime"><c:out value="${spittle.time}"/></span>
<span class="spittleLocation">
(<c:out value="${spittle.latitude}"/>,
<c:out value="${spittle.longitude}"/>)</span>
        </div>
    </li>
</c:forEach>
</body>
</html>