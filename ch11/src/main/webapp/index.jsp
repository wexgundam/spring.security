<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <body>
        <h2>Hello World!</h2>
        <div>
            <h2>注销</h2>
            <form action="/logout" method="post">
                <input type="submit" value="Logout">
            </form>
            <sec:authorize url="/admin">
                <h1>通过Url控制访问：</h1>
                <c:out value="/admin"/>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN') and fullyAuthenticated">
                <h1>通过Spring EL控制访问：</h1>
                <c:out value="hasRole('ROLE_ADMIN') and fullyAuthenticated"/>
            </sec:authorize>
        </div>
    </body>
</html>
