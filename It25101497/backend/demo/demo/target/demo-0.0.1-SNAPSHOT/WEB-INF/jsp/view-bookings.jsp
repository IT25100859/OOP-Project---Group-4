<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Bookings</title>
</head>
<body>
<h2>All Bookings (From File)</h2>
<table border="1">
    <thead>
    <tr>
        <th>Movie Name</th>
        <th>Customer Name</th>
        <th>Seats</th>
        <th>Total Price ($)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${bookings}">
        <tr>
            <td>${b.movieName}</td>
            <td>${b.customerName}</td>
            <td>${b.seats}</td>
            <td>${b.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<a href="/add-booking">Add Another Booking</a> | <a href="/">Home</a>
</body>
</html>

