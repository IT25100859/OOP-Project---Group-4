<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Zynix Movies</title>
    <style>
        /* Basic CSS for styling the dashboard */
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 30px; background-color: #f4f7f6; }
        h2 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }
        table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-top: 15px; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }
        th { background: #34495e; color: white; }

        /* Button styling */
        .btn { padding: 8px 15px; text-decoration: none; border-radius: 4px; color: white; font-weight: bold; font-size: 13px; margin-right: 5px; display: inline-block; }
        .btn-add { background: #2980b9; margin-bottom: 10px; }
        .btn-add:hover { background: #1f6391; }
        .btn-edit { background: #2ecc71; } /* Green button for edit action */
        .btn-del { background: #e74c3c; }  /* Red button for delete action */

        /* Table row hover effect */
        tr:hover { background-color: #f9f9f9; }
    </style>
</head>
<body>

<h2>🎬 Movie Reservation Dashboard</h2>

<a href="/add-booking-form" class="btn btn-add">+ Create New Reservation</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Movie Title</th>
        <th>Customer Name</th>
        <th>Seat No</th>
        <th>Ticket Price</th>
        <th>Date & Time</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%--
        Loop through the 'allBookings' list sent from the Spring Boot Controller.
        This uses JSTL (JavaServer Pages Standard Tag Library).
    --%>
    <c:forEach var="line" items="${allBookings}">

        <%-- Split the text file line by comma to get individual data parts --%>
        <c:set var="p" value="${line.split(',')}" />

        <%-- Ensure the line has all 7 data parts to prevent array index out of bounds errors --%>
        <c:if test="${p.length >= 7}">
            <tr>
                <td>${p[0]}</td> <td><b>${p[1]}</b></td> <td>${p[2]}</td> <td>${p[3]}</td> <td>Rs.${p[4]}</td> <td>${p[5]} @ ${p[6]}</td> <td>
                <a href="/edit-booking-form?id=${p[0]}" class="btn btn-edit">Edit</a>

                <a href="/delete-booking?id=${p[0]}" class="btn btn-del"
                   onclick="return confirm('Are you sure you want to cancel this booking?')">Delete</a>
            </td>
            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>

</body>
</html>