<%@ page language="java" contentType="text/html; charset=US-ASCII"
	
    pageEncoding="US-ASCII"%>
<%@ page import="control.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Registration</title>
</head>
<body>


<c:choose>
<c:when test="${found == 0}">
<center>
<h1>No notice found!</h1>

</center>
</c:when>
<c:otherwise>
<center>
<h1>Registration</h1></center><h3>
<c:forEach var="one" items="${list}" varStatus="loop">
	<h2>Details</h2>
	rid:${one._rid}<br>
	RegistrationNumber:${one.rego}<br>
	LastName:${one.last}<br>
	FirstName:${one.first}<br>
	DriversLicenseNo:${one.license}<br>
	Email:${one.email}<br>
	RegistrationValidTill:${one.valid}<br>
	
	
	
</c:forEach>
</h3>




</c:otherwise>
</c:choose>

<br><button onclick="goBack()">Go Back</button>

<script>
function goBack() {
    window.history.back();
}
</script>

</body>
</html>