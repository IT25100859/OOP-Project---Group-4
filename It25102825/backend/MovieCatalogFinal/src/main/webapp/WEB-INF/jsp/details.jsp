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
        .navbar { background: rgba(13,13,13,0.97); border-bottom: 1px solid #1a1a1a; padding: 0.75rem 2rem; }
        .navbar-brand { font-family: 'Bebas Neue'; font-size: 1.8rem; color: var(--gold) !important; letter-spacing: 3px; text-decoration: none; }
        .nav-link { color: #aaa !important; font-size: 0.85rem; text-decoration: none; }
        .nav-link:hover { color: var(--gold) !important; }

        /* Hero banner with poster blur */
        .detail-hero {
            background: linear-gradient(135deg, #0f1f40, #1a0a2e);
            padding: 2.5rem;
            border-radius: 12px 12px 0 0;
        }
        .movie-main-title { font-family: 'Bebas Neue'; font-size: clamp(2.5rem, 6vw, 4rem); letter-spacing: 2px; color: white; line-height: 1; }
        .poster-img { width: 180px; height: 260px; object-fit: cover; border-radius: 8px; border: 2px solid #1f2d4e; flex-shrink: 0; }
        .poster-placeholder { width: 180px; height: 260px; background: #0d1525; border-radius: 8px; border: 2px dashed #1f2d4e; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #333; font-size: 0.78rem; gap: 6px; flex-shrink: 0; }

        /* Info pills */
        .info-pill { background: rgba(255,255,255,0.07); border: 1px solid rgba(255,255,255,0.1); border-radius: 20px; padding: 4px 14px; font-size: 0.8rem; color: #bbb; }

        /* Detail rows */
        .detail-body { background: #16213e; border: 1px solid #1f2d4e; border-radius: 0 0 12px 12px; padding: 2rem 2.5rem; }
        .info-row { display: flex; justify-content: space-between; padding: 0.75rem 0; border-bottom: 1px solid #1f2d4e; font-size: 0.92rem; }
        .info-label { color: #555; font-weight: 600; text-transform: uppercase; letter-spacing: 0.08em; font-size: 0.75rem; }
        .info-value { color: #e0e0e0; text-align: right; }

        /* displayInfo() box */
        .display-info-box { background: #0d0d0d; border: 1px solid #333; border-left: 3px solid var(--gold); border-radius: 6px; padding: 1rem 1.25rem; font-family: monospace; font-size: 0.85rem; color: #aaa; margin-top: 1.5rem; }
        .display-info-label { color: #444; font-size: 0.72rem; margin-bottom: 4px; }

        /* Buttons */
        .btn-gold { background: var(--gold); color: #000; font-weight: 700; border: none; padding: 8px 22px; border-radius: 5px; text-decoration: none; display: inline-block; }
        .btn-gold:hover { background: #d4a900; color: #000; }
        .btn-back { background: #1f2d4e; color: #aaa; border: none; padding: 8px 20px; border-radius: 5px; text-decoration: none; display: inline-block; }
        .btn-back:hover { background: #2a3f6e; color: white; }

        /* Status badges */
        .badge-now    { background: #198754; padding: 5px 14px; border-radius: 20px; font-size: 0.8rem; color: white; }
        .badge-coming { background: #0d6efd; padding: 5px 14px; border-radius: 20px; font-size: 0.8rem; color: white; }
        .badge-old    { background: #6c757d; padding: 5px 14px; border-radius: 20px; font-size: 0.8rem; color: white; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar d-flex align-items-center">
    <a class="navbar-brand me-auto" href="/">CINEBOOK</a>
    <div class="d-flex gap-4 align-items-center">
        <a href="movie?action=list"      class="nav-link">Gallery</a>
        <a href="movie?action=adminForm" class="nav-link">+ Add Movie</a>
    </div>
</nav>

<div class="container py-5" style="max-width: 860px;">

    <c:if test="${empty movie}">
        <div class="alert alert-danger">Movie not found.</div>
        <a href="movie?action=list" class="btn-back">&#8592; Back to Gallery</a>
    </c:if>

    <c:if test="${not empty movie}">

        <!-- Hero section with poster + title side by side -->
        <div class="detail-hero">
            <div class="d-flex gap-4 align-items-start flex-wrap">

                <!-- Poster -->
                <c:choose>
                    <c:when test="${not empty movie.imagePath}">
                        <img src="${pageContext.request.contextPath}/movie-image/${movie.imagePath}"
                             alt="${movie.title}" class="poster-img">
                    </c:when>
                    <c:otherwise>
                        <div class="poster-placeholder">
                            <span style="font-size:2.5rem;">&#127916;</span>
                            No Poster
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- Title + meta -->
                <div class="flex-grow-1">
                    <div class="movie-main-title">${movie.title}</div>
                    <div class="d-flex flex-wrap gap-2 mt-2 mb-3">
                        <span class="info-pill">${movie.genre}</span>
                        <span class="info-pill">${movie.language}</span>
                        <span class="info-pill">${movie.duration} min</span>
                        <span class="info-pill">${movie.rating}</span>
                    </div>
                    <c:choose>
                        <c:when test="${movie.status == 'Now Showing'}"><span class="badge-now">&#9679; Now Showing</span></c:when>
                        <c:when test="${movie.status == 'Coming Soon'}"><span class="badge-coming">Coming Soon</span></c:when>
                        <c:otherwise><span class="badge-old">No Longer Showing</span></c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Detail rows -->
        <div class="detail-body">
            <div class="info-row">
                <span class="info-label">Movie ID</span>
                <span class="info-value" style="font-family:monospace; color:#666;">${movie.id}</span>
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

            <!-- Polymorphism demo box -->
            <div class="display-info-box">
                <div class="display-info-label">movie.displayInfo() &nbsp;&#8592;&nbsp; Polymorphism at work</div>
                ${movie.displayInfo()}
            </div>

            <!-- Buttons -->
            <div class="d-flex gap-3 mt-4">
                <a href="movie?action=list" class="btn-back">&#8592; Back</a>
                <a href="movie?action=edit&id=${movie.id}" class="btn-gold">Edit Movie</a>
            </div>
        </div>

    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
