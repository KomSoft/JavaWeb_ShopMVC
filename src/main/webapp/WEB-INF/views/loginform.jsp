<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Form</title>
</head>
<body>
<%@ include file="header.jsp" %>
    <center><form action='login' method='post'>
    <table border='0'>
       <tr>
          <td width='50%' align='right'>Login: </td>
          <td width='50%'><input type='text' name='login' /></td>
       </tr>
       <tr>
          <td align='right'>Password: </td>
          <td><input type='password' name='password' /></td>
          </tr>
       <tr>
          <td align='center' colspan='2'><input type='submit' value='Send' /></td>
       </tr>
    </table></form>
    <br>
    ${loginMessage}
    </center>
<%@ include file="footer.jsp" %>
</body>
</html>
