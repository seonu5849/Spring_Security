<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h4>/WEB-INF/views/login.jsp</h4>
<hr>
   <form method = "post">
        <table align = "center" cellpadding = "0" cellspacing="0">
            <tr>
                <td bgcolor = "orange">username</td>
                <td><input type = "text" name = "username"></td>
            </tr>

            <tr>
                <td bgcolor = "orange">password</td>
                <td><input type = "password" name = "password"></td>
            </tr>

            <tr>
                <td colspan="2" th:align="center">
                <input type = "submit" value="Login">
                </td>
            </tr>
        </table>

   </form>

