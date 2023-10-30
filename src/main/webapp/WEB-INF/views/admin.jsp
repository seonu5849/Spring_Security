<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h4>/WEB-INF/views/admin.jsp</h4>
<hr>

<a href="/loginSuccess">Go Back</a>

<hr>

<form action="/logout" method="GET">
    <input type="submit" value="Logout">
</form>