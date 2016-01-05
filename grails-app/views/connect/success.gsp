<%--
  Created by IntelliJ IDEA.
  User: Ferry
  Date: 12/2/15
  Time: 13:33
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Connect -> Success</title>
</head>

<body>
    Success! =)<br>

    Now test the connection:

    <ul>
        <li><g:link controller="connect" action="list" params="[schema_namespace: 'omh']">list</g:link> (you have to specify schema_namespace, schema_name and schema_version)</li>
        <li><g:link controller="connect" action="create">create</g:link> (parameters: source, value and unit)</li>
        <li><g:link controller="connect" action="show">show</g:link> (parameters: id)</li>
        <li><g:link controller="connect" action="delete">delete</g:link> (parameters: id)</li>
    </ul>
</body>
</html>