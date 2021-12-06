<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
    <jsp:include page="../header_links.jsp"/>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
<h2 style="text-align: center">Registration</h2>
<div style="width: 70%; margin: 0 auto;">
    <form:form action="/registration" method="POST" modelAttribute="user"
               style="display: flex;
        flex-direction: column; width: 50%; margin: 0 auto">
        <div style="margin-top: 5px; color:red"><form:errors
                path="login"></form:errors>
                ${loginError}</div>
        <label for="login">Enter your login:</label>
        <form:input type="text" path="login" placeholder="login"
                    class="form-control" required="required"
                    autofocus="true"></form:input>
        <label for="password">Enter your password:</label>
        <form:input type="password" path="password" class="form-control"
                    required="required"
                    placeholder="password"></form:input>
        <label for="password">Confirm your password:</label>
        <form:input type="password" path="passwordConfirm" class="form-control"
                    required="required"
                    placeholder="ConfirmPassword"></form:input>
        <div style="margin-top: 5px; color:red"><form:errors
                path="password"></form:errors>
                ${passwordError}</div>
        <div style="margin-top: 5px; color:red"><form:errors
                path="email"></form:errors>
                ${emailError}</div>
        <label for="email">Enter your email:</label>
        <form:input type="email" path="email" placeholder="email"
                    class="form-control" required="required"
        ></form:input>
        <div style="margin-top: 5px; color:red">
            <form:errors path="firstName"></form:errors></div>
        <label for="firstName">Enter your first name:</label>
        <form:input type="text" path="firstName" placeholder="firstName"
                    class="form-control" required="required"
        ></form:input>
        <div style="margin-top: 5px; color:red"><form:errors
                path="lastName"></form:errors></div>
        <label for="lastName">Enter your last name:</label>
        <form:input type="text" path="lastName" placeholder="lastName"
                    class="form-control" required="required"
        ></form:input>
        <div style="margin-top: 5px; color:red"><form:errors
                path="birthday"></form:errors>
                ${dateError}</div>
        <label for="birthday">Choose your birthday date:</label>
        <form:input type="date" path="birthday" placeholder="birthday"
                    class="form-control" required="required"
        ></form:input>
        <div style="margin-top: 5px; color:red"><span>${captchaError}</span>
        </div>
        <div class="g-recaptcha"
             data-sitekey="6LfH88MZAAAAAAvtYmhATJhggbeM8M0nX4eRrfVg"
             style="margin: 0 auto; margin-top: 10px;"></div>
        <button type="submit" class="btn btn-success"
                style="margin: 0 auto; margin-top: 10px; width: 30%">
            Register
        </button>
    </form:form>
    <div style="display: flex; flex-direction: row; width: 50%; margin: 0 auto;
            justify-content: center; margin-top: 10px">
        <a href="/home" class="btn btn-outline-danger" style="width: 49.6%;
                    margin-top: 5px" formnovalidate>Cancel</a>
    </div>
</div>
<jsp:include page="../body_links.jsp"/>
</body>
</html>