<%@page import="application.workshop.api.manager.CustomerManager"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<a href="localhost:8080/workshop-web/counter" target="_self">Counter</a><br/>
	<a href="localhost:8080/workshop-web/customerFrame.jsp" target="_self">Customer interface</a><br/>
	<a href="localhost:8080/workshop-web/workerFrame.jsp" target="_self">Worker interface</a><br/>
	<a href="localhost:8080/workshop-web/carFrame.jsp" target="_self">Car interface</a><br/>
	<a href="localhost:8080/workshop-web/customerOrderFrame.jsp" target="_self">Order interface</a>
</body>
</html>