<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Booking - Movie Booking System</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            width: 450px;
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
            font-size: 24px;
        }

        .input-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #34495e;
            font-weight: 600;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 15px;
            transition: border-color 0.3s ease;
            box-sizing: border-box;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #3498db;
        }

        .btn-submit {
            width: 100%;
            padding: 14px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s ease;
            margin-top: 10px;
        }

        .btn-submit:hover {
            background-color: #27ae60;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #7f8c8d;
            text-decoration: none;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>🎬 New Movie Reservation</h2>

    <form action="/save-booking" method="post">

        <div class="input-group">
            <label>Movie Name</label>
            <input type="text" name="movieName" placeholder="Ex: Avatar, Joker" required>
        </div>

        <div class="input-group" style="display: flex; gap: 15px;">
            <div style="flex: 1;">
                <label>Date</label>
                <input type="date" name="date" required>
            </div>
            <div style="flex: 1;">
                <label>Time</label>
                <input type="time" name="time" required>
            </div>
        </div>

        <div class="input-group">
            <label>Seat Type</label>
            <select name="seatType">
                <option value="Standard">Standard ($10.00)</option>
                <option value="VIP">VIP ($20.00)</option>
            </select>
        </div>

        <div class="input-group">
            <label>Seat Numbers (Separated by commas)</label>
            <input type="text" name="seatNumbers" placeholder="Ex: A12, A13" required>
        </div>

        <div class="input-group">
            <label>Number of Seats</label>
            <input type="number" name="numberOfSeats" min="1" value="1" required>
        </div>

        <button type="submit" class="btn-submit">Confirm Reservation</button>

        <a href="/" class="back-link">← Back to Dashboard</a>
    </form>
</div>

</body>
</html>