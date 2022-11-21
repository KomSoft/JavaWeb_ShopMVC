<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Success</title>
</head>
<body>
<%@ include file="header.jsp" %>
    <h3 align='center' style='color:blue;'>Access granted. Hello, ${sessionScope.authenticatedUser}!</h3>
<%@ include file="footer.jsp" %>
</body>
</html>