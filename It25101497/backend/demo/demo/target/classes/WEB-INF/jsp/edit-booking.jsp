<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Movie Reservation</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f4f7f6; display: flex; justify-content: center; padding: 40px; }
        .form-container { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 450px; }
        h2 { color: #2c3e50; text-align: center; margin-bottom: 25px; }
        .input-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #34495e; }
        input, select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; }
        .btn-submit { width: 100%; padding: 14px; background-color: #3498db; color: white; border: none; border-radius: 8px; cursor: pointer; font-size: 16px; font-weight: bold; }
        .btn-submit:hover { background-color: #2980b9; }
    </style>
</head>
<body>

<div class="form-container">
    <h2>✏️ Edit Reservation</h2>
    <form action="/update-booking" method="post">
        <input type="hidden" name="id" value="${booking.id}">

        <div class="input-group">
            <label>Movie Name</label>
            <input type="text" name="movieName" value="${booking.movieName}" required>
        </div>

        <div class="input-group" style="display: flex; gap: 15px;">
            <div style="flex: 1;">
                <label>Date</label>
                <input type="date" name="date" value="${booking.date}" required>
            </div>
            <div style="flex: 1;">
                <label>Time</label>
                <input type="time" name="time" value="${booking.time}" required>
            </div>
        </div>

        <div class="input-group">
            <label>Seat Type</label>
            <select name="seatType">
                <option value="Standard" ${booking.seatType == 'Standard' ? 'selected' : ''}>Standard</option>
                <option value="VIP" ${booking.seatType == 'VIP' ? 'selected' : ''}>VIP</option>
            </select>
        </div>

        <div class="input-group">
            <label>Seat Numbers (Ex: A5, B10)</label>
            <input type="text" name="seatNumbers" value="${booking.seatNumbers}" required>
        </div>

        <div class="input-group">
            <label>Number of Seats</label>
            <input type="number" name="numberOfSeats" value="${booking.numberOfSeats}" min="1" required>
        </div>

        <button type="submit" class="btn-submit">Update Reservation</button>
    </form>
</div>

</body>
</html>