<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="../header_links.jsp"/>
</head>
<body>
<div style="width: 70%; margin: 0 auto;">
    <div id="error" style="text-align: center; width: 40%; margin: 0 auto;">
        ${errorMessage}
    </div>
    <form action="/login" method="post" style="display: flex; flex-direction: column;
            width: 20%; margin: 20% auto">
        <label for="login">Enter your login:</label>
        <input id="login" type="text" name="username" class="form-control">
        <label for="password">Enter your password:</label>
        <input type="password" name="password" id="password"
               class="form-control">
        <div style="display: inline-block">
            <button type="submit" class="btn btn-success" style="width: 49%;
                    margin-top: 5px">Sign in
            </button>
            <a href="${pageContext.request.contextPath}/registration"
               class="btn btn-primary"
               style="width: 49%;
            margin-top: 5px">Registration</a>
        </div>
    </form>
</div>
<jsp:include page="../body_links.jsp"/>
</body>
</html>