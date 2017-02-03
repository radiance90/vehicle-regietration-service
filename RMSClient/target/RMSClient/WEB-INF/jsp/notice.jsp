<%@ page language="java" contentType="text/html; charset=US-ASCII"
	
    pageEncoding="US-ASCII"%>
<%@ page import="control.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Notice</title>
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
<h1>Notice</h1></center><h3>
<c:forEach var="nid" items="${list}" varStatus="loop">
	<h2>Details</h2>
	rid:${nid._rid}<br>
	nid:${nid._nid}<br>
	Status:${nid.status}<br>
	
	
	
</c:forEach>
</h3>



<h2>Actions</h2>
<h3>
<c:forEach var="nid" items="${list2}" varStatus="loop">
	
	
	<a href="${nid.link}">${nid.action}</a>
	
	
</c:forEach>
<a href="http://localhost:8080/RMSClient/car?rid=${list[0]._rid }">car registration</a>
</h3>
</c:otherwise>
</c:choose>
<c:if test="${ role == 'officer' }">
<br><h3><a href="http://localhost:8080/RMSClient/list">Go back</a></h3>
</c:if>

</body>
</html>