<%@ page import="com.nixsolutions.crudapp.entity.User" %>
<%@  page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>
<html>
<head>
    <title>Main page</title>
    <jsp:include page="../header_links.jsp"/>
</head>
<body>
    <jsp:include page="nav_bar.jsp"/>
        <div>
            <ex:users_list users="${users}"/>
        </div>
    <style>
        #users {
            width: 70%;
            margin: 0 auto;
        }
    </style>
    <script type="text/javascript">
        function confirm_click() {
            return confirm("Are you sure ?");
        }
    </script>
    <jsp:include page="../body_links.jsp"/>
</body>
</html>
