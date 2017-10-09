<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	CorpseRestFull
</h1>
<%
   Date date = new Date();
   out.print("<P>  The time on the server is " + date.toString() + "</p>");
%>
</body>
</html>
