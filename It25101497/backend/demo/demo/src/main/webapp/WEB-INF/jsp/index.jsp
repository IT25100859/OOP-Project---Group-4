<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cinema Management Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary: #e74c3c;
            --secondary: #2c3e50;
            --light: #ecf0f1;
            --dark: #1a1a1a;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }

        /* Hero Section */
        .hero {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)),
            url('https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            height: 400px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            color: white;
            text-align: center;
        }

        .hero h1 { font-size: 3.5rem; margin: 0; text-transform: uppercase; letter-spacing: 3px; }
        .hero p { font-size: 1.2rem; opacity: 0.9; margin-top: 10px; }

        /* Dashboard Container */
        .container {
            max-width: 1000px;
            margin: -50px auto 50px;
            padding: 20px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
        }

        /* Action Cards */
        .card {
            background: white;
            border-radius: 20px;
            padding: 40px;
            text-align: center;
            transition: all 0.3s ease;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            border-bottom: 5px solid transparent;
        }

        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 40px rgba(0,0,0,0.12);
        }

        .card.book { border-color: var(--primary); }
        .card.view { border-color: #3498db; }

        .card i {
            font-size: 50px;
            margin-bottom: 20px;
        }

        .card.book i { color: var(--primary); }
        .card.view i { color: #3498db; }

        .card h3 { font-size: 24px; color: var(--secondary); margin-bottom: 15px; }
        .card p { color: #7f8c8d; line-height: 1.6; margin-bottom: 25px; }

        .btn {
            display: inline-block;
            padding: 12px 30px;
            border-radius: 30px;
            text-decoration: none;
            font-weight: bold;
            text-transform: uppercase;
            transition: 0.3s;
        }

        .btn-book { background: var(--primary); color: white; }
        .btn-book:hover { background: #c0392b; box-shadow: 0 5px 15px rgba(231, 76, 60, 0.4); }

        .btn-view { background: #3498db; color: white; }
        .btn-view:hover { background: #2980b9; box-shadow: 0 5px 15px rgba(52, 152, 219, 0.4); }

        /* Footer Info */
        .footer {
            text-align: center;
            padding: 30px;
            color: #bdc3c7;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="hero">
    <h1>🎬 CINEMA PRO</h1>
    <p>Experience Movies Like Never Before</p>
</div>

<div class="container">
    <div class="card book">
        <i class="fas fa-ticket-alt"></i>
        <h3>New Reservation</h3>
        <p>Book seats for the latest blockbusters. Select your favorite spot and get your ticket instantly.</p>
        <a href="/add-booking" class="btn btn-book">Book Now</a>
    </div>

    <div class="card view">
        <i class="fas fa-list-ul"></i>
        <h3>Manage Bookings</h3>
        <p>View, update, or cancel existing reservations. Track your movie history and seat details.</p>
        <a href="/view-bookings" class="btn btn-view">View List</a>
    </div>
</div>

<div class="footer">
    &copy; 2026 Cinema Pro Reservation System | Powered by Spring Boot
</div>

</body>
</html>