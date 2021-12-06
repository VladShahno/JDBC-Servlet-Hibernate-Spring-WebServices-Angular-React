<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="../header_links.jsp"/>
</head>
<body>
    <c:set var="style" value="display: none;"/>
    <div style="width: 70%; margin: 0 auto;">
        <div id="error" style="text-align: center; width: 40%; margin: 0 auto;">
            <span>${error}</span>
        </div>
        <form action="${pageContext.request.contextPath}/login" method="post" style="display: flex; flex-direction: column; width: 20%; margin: 20% auto">
            <label for="login">Enter your login:</label>
            <input id="login" type="text" name="login" class="form-control">
            <label for="password">Enter your password:</label>
            <input type="password" name="password" id="password" class="form-control">
            <button type="submit" class="btn btn-success" style="width: 30%; margin-top: 5px">Sign in</button>
        </form>
    </div>
    <jsp:include page="../body_links.jsp"/>
</body>
</html>