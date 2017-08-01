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
		CustomerManager manager = (CustomerManager) ctx.lookup("java:app/workshop-ejb/DefaultCustomerManager");
	%>
	<%=manager.findAllCustomers()%>
		
	<h2>Customer Manager</h2>
	<form action="saveCustomer" method="POST">
		<input type="text" name="firstname" placeholder="First name"/><br/> 
		<input type="text" name="lastname" placeholder="Last name"/><br/> 
		<input type="text" name="address" placeholder="Address"/><br/> 
		<input type="text" name="emailaddress" placeholder="Email address"/><br/> 
		<input type="text" name="phone" placeholder="Phone"/><br/> 
		<input type="submit" value="save"/>
	</form>

</body>
</html>