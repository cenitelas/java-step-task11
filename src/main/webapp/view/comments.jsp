<%--
  Created by IntelliJ IDEA.
  User: vielen
  Date: 5/15/20
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Comments</title>
</head>
<body>
<c:if test="${comments != null}">
    <c:forEach items="${comments}" var="comment">
        ${comment.description}
        ${comment.user.username}
    </c:forEach>
</c:if>
</body>
</html>
