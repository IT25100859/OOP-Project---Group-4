<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Gallery – CineBook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        :root { --gold: #f5c518; --dark: #0d0d0d; }
        body { background: var(--dark); color: #e0e0e0; font-family: 'Inter', sans-serif; min-height: 100vh; }
        .navbar-brand { font-family: 'Bebas Neue'; font-size: 1.6rem; color: var(--gold) !important; letter-spacing: 2px; }
        .nav-link { color: #ccc !important; }
        .nav-link:hover { color: var(--gold) !important; }
        .search-bar { background: #1a1a2e; border: 1px solid #333; border-radius: 8px; padding: 1.25rem 1.5rem; }
        .form-control, .form-select {
            background: #0d0d0d; color: #e0e0e0; border: 1px solid #333;
        }
        .form-control:focus, .form-select:focus {
            background: #0d0d0d; color: #e0e0e0; border-color: var(--gold); box-shadow: none;
        }
        .form-select option { background: #0d0d0d; }
        .movie-card {
            background: #16213e;
            border: 1px solid #1f2d4e;
            border-radius: 10px;
            transition: transform 0.2s, border-color 0.2s;
            height: 100%;
        }
        .movie-card:hover { transform: translateY(-4px); border-color: var(--gold); }
        .badge-status-now    { background: #198754; }
        .badge-status-coming { background: #0d6efd; }
        .badge-status-no     { background: #6c757d; }
        .badge-type-action  { background: #dc3545; }
        .badge-type-comedy  { background: #fd7e14; }
        .badge-type-general { background: #6610f2; }
        .movie-title { font-family: 'Bebas Neue'; font-size: 1.3rem; letter-spacing: 1px; color: white; }
        .meta-text { color: #888; font-size: 0.8rem; }
        .btn-gold { background: var(--gold); color: #000; font-weight: 600; border: none; font-size: 0.82rem; padding: 5px 14px; border-radius: 4px; }
        .btn-gold:hover { background: #d4a900; color: #000; }
        .btn-edit { background: #1f3a5f; color: #7eb8f7; border: 1px solid #2a4f7e; font-size: 0.82rem; padding: 5px 14px; border-radius: 4px; }
        .btn-edit:hover { background: #2a4f7e; color: white; }
        .btn-del { background: #3a1a1a; color: #f77; border: 1px solid #5e2020; font-size: 0.82rem; padding: 5px 14px; border-radius: 4px; }
        .btn-del:hover { background: #5e2020; color: white; }
        .alert-custom { background: #1a2e1a; border: 1px solid #2d5a2d; color: #7ddc7d; border-radius: 6px; padding: .75rem 1rem; margin-bottom: 1rem; }
        .empty-state { text-align: center; color: #555; padding: 4rem 0; }
        .empty-state h3 { font-family: 'Bebas Neue'; font-size: 2rem; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg" style="background:#0d0d0d; border-bottom:1px solid #222;">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">CINEBOOK</a>
        <div class="d-flex gap-3 ms-auto align-items-center">
            <a href="movie?action=list"      class="nav-link">Gallery</a>
            <a href="movie?action=adminForm" class="nav-link">+ Add Movie</a>
        </div>
    </div>
</nav>

<div class="container py-4">

    <!-- Toast / Message -->
    <c:if test="${not empty param.msg}">
        <div class="alert-custom">✔ ${param.msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Search Bar -->
    <form action="movie" method="get" class="search-bar mb-4">
        <input type="hidden" name="action" value="search">
        <div class="row g-2 align-items-end">
            <div class="col-md-4">
                <label class="form-label text-secondary small">Search by Title</label>
                <input type="text" name="title" class="form-control" placeholder="e.g. Inception">
            </div>
            <div class="col-md-3">
                <label class="form-label text-secondary small">Filter by Genre</label>
                <select name="genre" class="form-select">
                    <option value="">All Genres</option>
                    <option>Action</option>
                    <option>Comedy</option>
                    <option>Drama</option>
                    <option>Horror</option>
                    <option>Thriller</option>
                    <option>Romance</option>
                    <option>Sci-Fi</option>
                    <option>Animation</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label text-secondary small">Filter by Language</label>
                <select name="language" class="form-select">
                    <option value="">All Languages</option>
                    <option>English</option>
                    <option>Sinhala</option>
                    <option>Tamil</option>
                    <option>Hindi</option>
                    <option>Korean</option>
                    <option>French</option>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn-gold w-100 py-2" style="border-radius:6px;">Search</button>
            </div>
        </div>
    </form>

    <!-- Section heading -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h5 style="color:#aaa; font-size:0.9rem; text-transform:uppercase; letter-spacing:2px;">
            <c:choose>
                <c:when test="${not empty searchLabel}">Results for: <span style="color:var(--gold)">${searchLabel}</span></c:when>
                <c:otherwise>All Movies</c:otherwise>
            </c:choose>
            &nbsp;<span style="color:#555;">( ${fn:length(movies)} found )</span>
        </h5>
        <a href="movie?action=list" class="text-secondary small">Clear filter</a>
    </div>

    <!-- Movie Grid -->
    <c:choose>
        <c:when test="${empty movies}">
            <div class="empty-state">
                <h3>No Movies Found</h3>
                <p>Try a different search or <a href="movie?action=adminForm" style="color:var(--gold)">add a movie</a>.</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 g-4">
                <c:forEach var="movie" items="${movies}">
                    <div class="col">
                        <div class="movie-card p-3">
                            <!-- Type & Status badges -->
                            <div class="d-flex gap-2 mb-2">
                                <span class="badge
                                    <c:choose>
                                        <c:when test="${movie.type == 'ACTION'}">badge-type-action</c:when>
                                        <c:when test="${movie.type == 'COMEDY'}">badge-type-comedy</c:when>
                                        <c:otherwise>badge-type-general</c:otherwise>
                                    </c:choose>">${movie.type}</span>
                                <span class="badge
                                    <c:choose>
                                        <c:when test="${movie.status == 'Now Showing'}">badge-status-now</c:when>
                                        <c:when test="${movie.status == 'Coming Soon'}">badge-status-coming</c:when>
                                        <c:otherwise>badge-status-no</c:otherwise>
                                    </c:choose>">${movie.status}</span>
                            </div>

                            <!-- Title -->
                            <div class="movie-title">${movie.title}</div>

                            <!-- Meta -->
                            <div class="meta-text mt-1">
                                ${movie.genre} &bull; ${movie.language} &bull; ${movie.duration} min &bull; ${movie.rating}
                            </div>

                            <!-- Extra attribute -->
                            <c:if test="${movie.extraAttribute != 'N/A'}">
                                <div class="meta-text mt-1" style="color:#aaa;">
                                    <c:choose>
                                        <c:when test="${movie.type == 'ACTION'}">Intensity: ${movie.extraAttribute}</c:when>
                                        <c:when test="${movie.type == 'COMEDY'}">Humor: ${movie.extraAttribute}</c:when>
                                    </c:choose>
                                </div>
                            </c:if>

                            <!-- ID -->
                            <div class="meta-text mt-1" style="color:#444;">${movie.id}</div>

                            <!-- Actions -->
                            <div class="d-flex gap-2 mt-3 flex-wrap">
                                <a href="movie?action=details&id=${movie.id}" class="btn-gold">Details</a>
                                <a href="movie?action=edit&id=${movie.id}"    class="btn-edit">Edit</a>
                                <a href="movie?action=delete&id=${movie.id}"  class="btn-del"
                                   onclick="return confirm('Delete ${movie.title}?')">Delete</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
