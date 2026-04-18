<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Booking - Zynix</title>
    <style>
        body { font-family: sans-serif; margin: 50px; background-color: #f4f7f6; }
        .edit-container { background: #fff; padding: 20px; border-left: 5px solid #f1c40f; }
    </style>
</head>
<body>
<div class="edit-container">
    <h2>Edit Booking ID: ${bookingId}</h2>
    <p>Updating records in <b>bookings.txt</b> file...</p>

    <form action="/update-booking" method="post">
        <input type="hidden" name="id" value="${bookingId}">

        <label>Customer Name:</label><br>
        <input type="text" name="name" placeholder="Update Name" required><br><br>

        <label>Movie:</label><br>
        <input type="text" name="movie" placeholder="Update Movie" required><br><br>

        <label>Date:</label><br>
        <input type="date" name="date" required><br><br>

        <label>Time:</label><br>
        <input type="time" name="time" required><br><br>

        <button type="submit" style="background:#f1c40f; padding:10px; cursor:pointer;">Update Record</button>
    </form>

    <br>
    <a href="/view-bookings">Cancel and Go Back</a>
</div>
</body>
</html>