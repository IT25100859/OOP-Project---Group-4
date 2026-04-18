<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>New Reservation - Zynix</title>
    <style>
        body { font-family: sans-serif; margin: 50px; background-color: #f4f7f6; }
        .form-container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 400px; }
        input, select { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-save { background: #2ecc71; color: white; border: none; cursor: pointer; font-weight: bold; }
    </style>
</head>
<body>
<div class="form-container">
    <h3>🎬 Add New Booking</h3>
    <form action="/save-booking" method="post">
        <input type="text" name="name" placeholder="Customer Name" required>
        <input type="text" name="movie" placeholder="Movie Title" required>
        <input type="text" name="seatNo" placeholder="Seat Number (e.g. A10)" required>
        <select name="type">
            <option value="Standard">Standard (Rs. 780)</option>
            <option value="VIP">VIP (Rs. 1500)</option>
        </select>
        <input type="date" name="date" required>
        <input type="time" name="time" required>
        <button type="submit" class="btn-save">Confirm Reservation</button>
    </form>
    <br>
    <a href="/view-bookings">Back to Dashboard</a>
</div>
</body>
</html>