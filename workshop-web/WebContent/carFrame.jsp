<%@page import="application.workshop.api.manager.CarManager"%>
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
	<%
		Context ctx = new InitialContext();
	CarManager manager = (CarManager) ctx.lookup("java:app/workshop-ejb/DefaultCarManager");
	%>
	<%=manager.findAllCars()%>
	<br/> Customers <br/>
	<%
	CustomerManager customerManager = (CustomerManager) ctx.lookup("java:app/workshop-ejb/DefaultCustomerManager");
	%>
	<%=customerManager.findAllCustomers()%>
		
	<h2>Car Manager</h2>
	<form action="saveCar" method="POST">
		<input type="text" name="name" placeholder="Car name"/><br/> 
		<input type="text" name="vin" placeholder="VIN number"/><br/> 
		<input type="text" name="customerid" placeholder="ID customer"/><br/> 
		<input type="submit" value="save"/>
	</form>

</body>
</html>