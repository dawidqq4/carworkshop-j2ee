<%@page import="application.workshop.api.manager.WorkerManager"%>
<%@page import="application.workshop.api.manager.CarManager"%>
<%@page import="application.workshop.api.manager.CustomerOrderManager"%>
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
	Workers<br/>
	<%
		Context ctx = new InitialContext();
		WorkerManager workerManager = (WorkerManager) ctx.lookup("java:app/workshop-ejb/DefaultWorkerManager");
	%>
	<%=workerManager.findAllWorkers()%>

	<br/><br/> Cars <br/>
	<%
		CarManager manager = (CarManager) ctx.lookup("java:app/workshop-ejb/DefaultCarManager");
	%>
	<%=manager.findAllCars()%>
	
	<br/><br/> Orders <br/>
	<%
		CustomerOrderManager customerOrderManager = (CustomerOrderManager) ctx.lookup("java:app/workshop-ejb/DefaultCustomerOrderManager");
	%>
	<%=customerOrderManager.findAllCustomerOrders()%>
		
		
	<h2>Order Manager</h2>
	<form action="saveOrder" method="POST">
		<input type="text" name="description" placeholder="Description"/><br/> 
		<input type="text" name="price" placeholder="Price"/><br/> 
		<input type="text" name="idWorker" placeholder="Worker ID"/><br/> 
		<input type="text" name="idCar" placeholder="Car ID"/><br/> 
		<input type="submit" value="save"/>
	</form>

</body>
</html>