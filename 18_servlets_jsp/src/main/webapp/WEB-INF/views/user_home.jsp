<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../header_links.jsp"/>
    <title>Main page</title>
</head>
<body>
    <jsp:include page="user_nav_bar.jsp"/>
    <div style="margin: auto; width: 400px;padding: 10px;">
        <h1>Hello, ${name}!</h1>
    </div>
    <jsp:include page="../body_links.jsp"/>
</body>
</html>