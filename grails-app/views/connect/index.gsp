<%--
  Created by IntelliJ IDEA.
  User: Ferry
  Date: 12/2/15
  Time: 11:50
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Connect</title>
</head>

<body>
    <g:link target="_blank" controller="oauth" action="authenticate" params="${[provider:'nrc']}">NRC Connect</g:link>
</body>
</html>