<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link"
               href="${pageContext.request.contextPath}/login?logout=true">Logout</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Main
                page</a>
        </li>
    </ul>
</div>
<div style="float: left; margin-left: 14px;">
    <p>
        <mark>${role} ${name}</mark>
    </p>
</div>