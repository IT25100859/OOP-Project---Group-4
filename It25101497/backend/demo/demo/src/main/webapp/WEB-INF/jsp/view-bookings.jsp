<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Bookings - Cinema Booking System</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f4f7f6; padding: 40px; display: flex; flex-direction: column; align-items: center; }

        h2 { color: #2c3e50; margin-bottom: 10px; }

        /* Add Button Styling */
        .add-btn {
            background-color: #3498db;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            margin-bottom: 20px;
            display: inline-block;
            transition: 0.3s;
            box-shadow: 0 4px 15px rgba(52, 152, 219, 0.3);
        }
        .add-btn:hover { background-color: #2980b9; transform: translateY(-2px); }

        .table-container { width: 95%; background: white; padding: 25px; border-radius: 15px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); }

        table { width: 100%; border-collapse: collapse; }

        th { background: #34495e; color: white; padding: 15px; text-align: left; font-size: 14px; }

        td { padding: 15px; border-bottom: 1px solid #eee; color: #555; }

        tr:hover { background-color: #fcfcfc; }

        /* Seat Number Badge */
        .seat-badge {
            background: #e67e22;
            color: white;
            padding: 4px 10px;
            border-radius: 5px;
            font-weight: bold;
            font-size: 13px;
        }

        .btn { padding: 8px 15px; border-radius: 6px; text-decoration: none; color: white; font-weight: bold; font-size: 13px; }
        .btn-edit { background: #2ecc71; margin-right: 5px; }
        .btn-edit:hover { background: #27ae60; }
        .btn-delete { background: #e74c3c; }
        .btn-delete:hover { background: #c0392b; }
    </style>
</head>
<body>

<h2>🎥 Movie Reservation Management</h2>
<p style="color: #7f8c8d; margin-bottom: 25px;">Manage all your cinema seat bookings here.</p>

<a href="/add-booking" class="add-btn">+ Add New Booking</a>

<div class="table-container">
    <table>
        <thead>
        <tr>
            <th>Movie Name</th>
            <th>Show Date</th>
            <th>Time</th>
            <th>Type</th>
            <th>Seat Numbers</th> <th>Qty</th>
            <th>Total Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="booking" items="${bookings}">
            <tr>
                <td style="font-weight: bold; color: #2c3e50;">${booking.movieName}</td>
                <td>${booking.date}</td>
                <td>${booking.time}</td>
                <td>${booking.seatType}</td>
                <td><span class="seat-badge">${booking.seatNumbers}</span></td>
                <td>${booking.numberOfSeats}</td>
                <td style="font-weight: bold; color: #27ae60;">$${booking.totalPrice}</td>
                <td>
                    <a href="/edit-booking?id=${booking.id}" class="btn btn-edit">Update</a>
                    <a href="/delete-booking?id=${booking.id}" class="btn btn-delete" onclick="return confirm('Delete this reservation?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<br>
<a href="/" style="text-decoration: none; color: #95a5a6; font-size: 14px;">← Back to Dashboard</a>

</body>
</html>