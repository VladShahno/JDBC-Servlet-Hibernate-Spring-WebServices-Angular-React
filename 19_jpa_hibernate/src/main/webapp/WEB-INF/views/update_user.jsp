<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit user: ${user.firstName}</title>
    <jsp:include page="../header_links.jsp"/>
</head>
<body>
<jsp:include page="nav_bar.jsp"/>
<h2 style="text-align: center">Edit user</h2>
<div style="width: 70%; margin: 0 auto;">
    <form class="ajax-form" id="pwreset"
          action="${pageContext.request.contextPath}/users/update" method="post"
          style="display: flex; flex-direction: column; width: 50%; margin: 0 auto">
        <input type="hidden" id="id" name="id" required readonly
               value=${user.id}>
        <label for="user_login">Enter your login:</label>
        <input type="text" name="login" id="user_login" class="form-control"
               required value="${user.login}" readonly>
        <label for="user_password">Enter your password:</label>
        <input type="password" name="password" id="user_password"
               class="form-control" required value="${user.password}">
        <label for="password">Confirm your password:</label>
        <input type="password" name="password" id="new_user_password"
               class="form-control" required value="${user.password}">
        <div id="divCheckPassword"></div>
        <div><span>${emailError}</span></div>
        <label for="user_email">Enter your email:</label>
        <input type="email" name="email" id="user_email" class="form-control"
               required value="${user.email}">
        <div style="margin-top: 5px" id="firstNameError">
            <span>${firstNameError}</span></div>
        <label for="user_firstname">Enter your first name:</label>
        <input type="text" name="first_name" id="user_firstname"
               class="form-control"
               required value="${user.firstName}">
        <div style="margin-top: 5px" id="lastNameError">
            <span>${lastNameError}</span></div>
        <label for="user_lastname">Enter your last name:</label>
        <input type="text" name="last_name" id="user_lastname"
               class="form-control"
               required value="${user.lastName}">
        <div id="dateError"><span>${dateError}</span></div>
        <label for="user_birthday">Choose your birthday date:</label>
        <input type="date" name="birthday" id="user_birthday"
               class="form-control"
               required value="${user.birthday}">
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
                    margin-top: 5px">Edit user
            </button>
            <a href="${pageContext.request.contextPath}/home"
               class="btn btn-outline-danger" style="width: 49.6%;
                    margin-top: 5px">Cancel</a>
        </div>
    </form>
</div>
<script type="text/javascript">
    function verifyPassword() {
        let pass1 = document.getElementById("user_password").value;
        let pass2 = document.getElementById("new_user_password").value;
        let match = true;
        if (pass1 != pass2) {
            $("#divCheckPassword").html("Passwords do not match!")
            document.getElementById("user_password").style.borderColor = "#ff0000";
            document.getElementById("new_user_password").style.borderColor = "#ff0000";
            match = false;
        } else {
            $("#divCheckPassword").html(" ");
            document.getElementById("user_password").style.borderColor = "green";
            document.getElementById("new_user_password").style.borderColor = "green";
        }
        return match;
    }

    document.getElementById('pwreset').onsubmit = verifyPassword;
    $(document).ready(function () {
        $("#new_user_password").keyup(isPasswordMatch);
    });
</script>
<jsp:include page="../body_links.jsp"/>
</body>
</html>