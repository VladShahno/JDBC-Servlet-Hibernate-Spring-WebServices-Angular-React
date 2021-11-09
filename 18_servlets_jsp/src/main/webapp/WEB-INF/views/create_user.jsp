<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add user</title>
    <jsp:include page="../header_links.jsp"/>
</head>
<body>
    <jsp:include page="nav_bar.jsp"/>
    <c:set var="style" value="display: none;"/>
    <h2 style="text-align: center">Add user</h2>
    <div style="width: 70%; margin: 0 auto;">
        <div style="text-align: center; width: 40%; margin: 0 auto;" id="message">
            <span id="success-message">${message}</span>
        </div>
        <form action="${pageContext.request.contextPath}/new" method="post" id="reg-form" style="display: flex;
        flex-direction: column; width: 50%; margin: 0 auto">
            <div id="loginError"><span>${loginError}</span></div>
            <label for="user_login">Enter your login:</label>
            <input type="text" name="login" id="user_login" class="form-control" required>
            <label for="user_password">Enter your password:</label>
            <input type="password" name="password" id="user_password" class="form-control" required>
            <div style="margin-top: 5px" id="emailError"><span>${emailError}</span></div>
            <label for="user_email">Enter your email:</label>
            <input type="email" name="email" id="user_email" class="form-control" required>
            <label for="user_firstname">Enter your first name:</label>
            <input type="text" name="first_name" id="user_firstname" class="form-control" required>
            <label for="user_lastname">Enter your last name:</label>
            <input type="text" name="last_name" id="user_lastname" class="form-control" required>
            <label for="user_birthday">Choose your birthday date:</label>
            <input type="date" name="birthday" id="user_birthday" class="form-control" required>
            <label for="role">Select your role:</label>
            <select name="role" class="form-control">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}"
                        <c:if test="${role.id eq selectedRoleId}">selected="selected"</c:if>>
                            ${role.name}
                    </option>
                </c:forEach>
            </select>
            <div style="display: inline-block">
                <button type="submit" class="btn btn-success" style="width: 49.6%;
                    margin-top: 5px" onclick="click()">Add user</button>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-danger" style="width: 49.6%;
                    margin-top: 5px">Cancel</a>
            </div>
        </form>
    </div>
    <jsp:include page="../body_links.jsp"/>
</body>
</html>
