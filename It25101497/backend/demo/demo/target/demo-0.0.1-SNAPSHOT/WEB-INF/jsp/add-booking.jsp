<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Booking</title>
</head>
<body>
<h2>Add New Booking</h2>
<form action="/save-booking" method="post">
    <label>Movie Name:</label><br>
    <input type="text" name="movieName" required><br><br>

    <label>Customer Name:</label><br>
    <input type="text" name="customerName" required><br><br>

    <label>Number of Seats:</label><br>
    <input type="number" name="seats" required><br><br>

    <button type="submit">Save Booking</button>
</form>
<br>
<a href="/">Back to Home</a>
</body>
</html>