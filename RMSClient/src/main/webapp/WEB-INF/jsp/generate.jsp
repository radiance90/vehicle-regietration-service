<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="control.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Results</title>
</head>
<body>


<c:choose>
<c:when test="${found == 0}">
<center>
<h1>No notice generated!</h1>

</center>
</c:when>
<c:otherwise>
<center>
<h1>Generated notices</h1>

</center>
<c:forEach var="notice" items="${returnData}" varStatus="loop">
	<h3>
	
	
	${notice._nid}<br>
	</h3>
</c:forEach>

</c:otherwise>
</c:choose>

<br><h3><a href="http://localhost:8080/RMSClient/officer_home">Go back</a></h3>


</body>
</html>