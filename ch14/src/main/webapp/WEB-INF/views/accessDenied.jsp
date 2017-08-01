<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/1
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied</title>
</head>
<body>
    <h1>Access Denied</h1>
    <p>
        Access to the specified resource has bean denied for the following reason:<strong>${errorDetails}</strong>
    </p>
    <em>Error Details(for Support Purpose only:)</em>
    <br />
    <blockquote>
        <pre>${errorTrace}</pre>
    </blockquote>
</body>
</html>
