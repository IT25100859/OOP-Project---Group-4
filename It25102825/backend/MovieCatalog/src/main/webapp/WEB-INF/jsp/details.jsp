<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${movie.title} – CineBook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        :root { --gold: #f5c518; --dark: #0d0d0d; }
        body { background: var(--dark); color: #e0e0e0; font-family: 'Inter', sans-serif; min-height: 100vh; }
        .navbar-brand { font-family: 'Bebas Neue'; font-size: 1.6rem; color: var(--gold) !important; letter-spacing: 2px; }
        .nav-link { color: #ccc !important; }
        .detail-card {
            background: #16213e;
            border: 1px solid #1f2d4e;
            border-radius: 12px;
            overflow: hidden;
        }
        .detail-header {
            background: linear-gradient(135deg, #0f1f40, #1a0a2e);
            padding: 2.5rem;
            border-bottom: 1px solid #1f2d4e;
        }
        .movie-main-title {
            font-family: 'Bebas Neue';
            font-size: clamp(2.5rem, 6vw, 4rem);
            letter-spacing: 2px;
            color: white;
            line-height: 1;
        }
        .row-info { display: flex; gap: 1rem; flex-wrap: wrap; margin-top: 1rem; }
        .info-pill {
            background: rgba(255,255,255,0.07);
            border: 1px solid rgba(255,255,255,0.1);
            border-radius: 20px;
            padding: 4px 14px;
            font-size: 0.82rem;
            color: #bbb;
        }
        .detail-body { padding: 2rem 2.5rem; }
        .info-row {
            display: flex;
            justify-content: space-between;
            padding: 0.75rem 0;
            border-bottom: 1px solid #1f2d4e;
            font-size: 0.95rem;
        }
        .info-label { color: #666; font-weight: 600; text-transform: uppercase; letter-spacing: 0.08em; font-size: 0.78rem; }
        .info-value { color: #e0e0e0; }
        .badge-status-now    { background: #198754; padding: 6px 16px; border-radius: 20px; font-size: 0.85rem; }
        .badge-status-coming { background: #0d6efd; padding: 6px 16px; border-radius: 20px; font-size: 0.85rem; }
        .badge-status-no     { background: #6c757d; padding: 6px 16px; border-radius: 20px; font-size: 0.85rem; }
        .display-info-box {
            background: #0d0d0d;
            border: 1px solid #333;
            border-left: 3px solid var(--gold);
            border-radius: 6px;
            padding: 1rem 1.25rem;
            font-family: monospace;
            font-size: 0.88rem;
            color: #aaa;
            margin-top: 1.5rem;
        }
        .btn-gold { background: var(--gold); color: #000; font-weight: 600; border: none; padding: 8px 20px; border-radius: 5px; text-decoration: none; }
        .btn-back { background: #1f2d4e; color: #aaa; border: none; padding: 8px 20px; border-radius: 5px; text-decoration: none; }
        .btn-back:hover { background: #2a3f6e; color: white; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg" style="background:#0d0d0d; border-bottom:1px solid #222;">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">CINEBOOK</a>
        <div class="d-flex gap-3 ms-auto">
            <a href="movie?action=list" class="nav-link">Gallery</a>
            <a href="movie?action=adminForm" class="nav-link">+ Add Movie</a>
        </div>
    </div>
</nav>

<div class="container py-5" style="max-width: 800px;">

    <c:if test="${empty movie}">
        <div class="alert alert-danger">Movie not found.</div>
        <a href="movie?action=list" class="btn-back">← Back to Gallery</a>
    </c:if>

    <c:if test="${not empty movie}">
        <div class="detail-card">

            <!-- Header -->
            <div class="detail-header">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-2">
                    <div>
                        <div class="movie-main-title">${movie.title}</div>
                        <div class="row-info">
                            <span class="info-pill">${movie.genre}</span>
                            <span class="info-pill">${movie.language}</span>
                            <span class="info-pill">${movie.duration} min</span>
                            <span class="info-pill">${movie.rating}</span>
                            <span class="info-pill">${movie.id}</span>
                        </div>
                    </div>
                    <span class="badge
                        <c:choose>
                            <c:when test="${movie.status == 'Now Showing'}">badge-status-now</c:when>
                            <c:when test="${movie.status == 'Coming Soon'}">badge-status-coming</c:when>
                            <c:otherwise>badge-status-no</c:otherwise>
                        </c:choose>">${movie.status}</span>
                </div>
            </div>

            <!-- Body -->
            <div class="detail-body">
                <div class="info-row">
                    <span class="info-label">Movie ID</span>
                    <span class="info-value">${movie.id}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Type</span>
                    <span class="info-value">${movie.type}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Genre</span>
                    <span class="info-value">${movie.genre}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Duration</span>
                    <span class="info-value">${movie.duration} minutes</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Language</span>
                    <span class="info-value">${movie.language}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Age Rating</span>
                    <span class="info-value">${movie.rating}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Status</span>
                    <span class="info-value">${movie.status}</span>
                </div>

                <!-- Subclass-specific attribute -->
                <c:if test="${movie.extraAttribute != 'N/A'}">
                    <div class="info-row">
                        <span class="info-label">
                            <c:choose>
                                <c:when test="${movie.type == 'ACTION'}">Action Intensity</c:when>
                                <c:when test="${movie.type == 'COMEDY'}">Humor Style</c:when>
                            </c:choose>
                        </span>
                        <span class="info-value">${movie.extraAttribute}</span>
                    </div>
                </c:if>

                <!-- Polymorphism demonstration box -->
                <div class="display-info-box">
                    <div style="color:#666; font-size:0.75rem; margin-bottom:4px;">
                        movie.displayInfo()  &nbsp;←&nbsp; Polymorphism at work
                    </div>
                    ${movie.displayInfo()}
                </div>

                <!-- Buttons -->
                <div class="d-flex gap-3 mt-4">
                    <a href="movie?action=list" class="btn-back">← Back</a>
                    <a href="movie?action=edit&id=${movie.id}" class="btn-gold">Edit Movie</a>
                </div>
            </div>
        </div>
    </c:if>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
