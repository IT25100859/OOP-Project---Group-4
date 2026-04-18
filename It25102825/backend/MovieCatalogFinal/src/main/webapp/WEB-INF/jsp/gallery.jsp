<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

        .navbar { background: rgba(13,13,13,0.97); border-bottom: 1px solid #1a1a1a; padding: 0.75rem 2rem; }
        .navbar-brand { font-family: 'Bebas Neue'; font-size: 1.8rem; color: var(--gold) !important; letter-spacing: 3px; text-decoration: none; }
        .nav-link { color: #aaa !important; font-size: 0.85rem; letter-spacing: 1px; text-decoration: none; }
        .nav-link:hover { color: var(--gold) !important; }

        /* Search bar */
        .search-bar { background: #16213e; border: 1px solid #1f2d4e; border-radius: 10px; padding: 1.25rem 1.5rem; }
        .form-control, .form-select { background: #0d0d0d; color: #e0e0e0; border: 1px solid #333; border-radius: 6px; }
        .form-control:focus, .form-select:focus { background: #0d0d0d; color: #e0e0e0; border-color: var(--gold); box-shadow: none; }
        .form-select option { background: #0d0d0d; }
        .form-label { color: #777; font-size: 0.78rem; font-weight: 600; letter-spacing: 0.05em; text-transform: uppercase; }
        .btn-search { background: var(--gold); color: #000; font-weight: 700; border: none; border-radius: 6px; width: 100%; padding: 0.6rem; }
        .btn-search:hover { background: #d4a900; }

        /* Movie cards */
        .movie-card { background: #16213e; border: 1px solid #1f2d4e; border-radius: 10px; transition: transform 0.2s, border-color 0.2s; height: 100%; overflow: hidden; }
        .movie-card:hover { transform: translateY(-4px); border-color: var(--gold); }
        .movie-card img { width: 100%; height: 200px; object-fit: cover; display: block; }
        .no-poster { width: 100%; height: 200px; background: #0d1525; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #333; font-size: 0.75rem; gap: 6px; }
        .card-body-inner { padding: 12px; }
        .movie-title { font-family: 'Bebas Neue'; font-size: 1.2rem; letter-spacing: 1px; color: white; line-height: 1.2; }
        .meta-text { color: #666; font-size: 0.75rem; margin-top: 4px; }

        /* Badges */
        .badge-type-action  { background: #dc3545; font-size: 0.62rem; padding: 3px 8px; border-radius: 10px; color: white; }
        .badge-type-comedy  { background: #fd7e14; font-size: 0.62rem; padding: 3px 8px; border-radius: 10px; color: white; }
        .badge-type-general { background: #6610f2; font-size: 0.62rem; padding: 3px 8px; border-radius: 10px; color: white; }
        .badge-status-now    { background: #198754; font-size: 0.62rem; padding: 3px 8px; border-radius: 10px; color: white; }
        .badge-status-coming { background: #0d6efd; font-size: 0.62rem; padding: 3px 8px; border-radius: 10px; color: white; }
        .badge-status-no     { background: #6c757d; font-size: 0.62rem; padding: 3px 8px; border-radius: 10px; color: white; }

        /* Action buttons */
        .btn-view { background: var(--gold); color: #000; font-weight: 700; border: none; font-size: 0.78rem; padding: 5px 12px; border-radius: 4px; text-decoration: none; }
        .btn-view:hover { background: #d4a900; color: #000; }
        .btn-edit { background: #1f3a5f; color: #7eb8f7; border: 1px solid #2a4f7e; font-size: 0.78rem; padding: 5px 12px; border-radius: 4px; text-decoration: none; }
        .btn-edit:hover { background: #2a4f7e; color: white; }
        .btn-del { background: #3a1a1a; color: #f77; border: 1px solid #5e2020; font-size: 0.78rem; padding: 5px 12px; border-radius: 4px; text-decoration: none; }
        .btn-del:hover { background: #5e2020; color: white; }

        /* Misc */
        .alert-success-custom { background: #1a2e1a; border: 1px solid #2d5a2d; color: #7ddc7d; border-radius: 6px; padding: .75rem 1rem; margin-bottom: 1rem; }
        .empty-state { text-align: center; color: #444; padding: 5rem 0; }
        .empty-state h3 { font-family: 'Bebas Neue'; font-size: 2rem; }
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

<div class="container py-4">

    <!-- Success message -->
    <c:if test="${not empty param.msg}">
        <div class="alert-success-custom">&#10004; ${param.msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Search bar -->
    <form action="movie" method="get" class="search-bar mb-4">
        <input type="hidden" name="action" value="search">
        <div class="row g-2 align-items-end">
            <div class="col-md-4">
                <label class="form-label">Search by Title</label>
                <input type="text" name="title" class="form-control" placeholder="e.g. Inception" value="${param.title}">
            </div>
            <div class="col-md-3">
                <label class="form-label">Filter by Genre</label>
                <select name="genre" class="form-select">
                    <option value="">All Genres</option>
                    <option ${param.genre == 'Action'    ? 'selected' : ''}>Action</option>
                    <option ${param.genre == 'Comedy'    ? 'selected' : ''}>Comedy</option>
                    <option ${param.genre == 'Drama'     ? 'selected' : ''}>Drama</option>
                    <option ${param.genre == 'Horror'    ? 'selected' : ''}>Horror</option>
                    <option ${param.genre == 'Thriller'  ? 'selected' : ''}>Thriller</option>
                    <option ${param.genre == 'Romance'   ? 'selected' : ''}>Romance</option>
                    <option ${param.genre == 'Sci-Fi'    ? 'selected' : ''}>Sci-Fi</option>
                    <option ${param.genre == 'Animation' ? 'selected' : ''}>Animation</option>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Filter by Language</label>
                <select name="language" class="form-select">
                    <option value="">All Languages</option>
                    <option ${param.language == 'English' ? 'selected' : ''}>English</option>
                    <option ${param.language == 'Sinhala' ? 'selected' : ''}>Sinhala</option>
                    <option ${param.language == 'Tamil'   ? 'selected' : ''}>Tamil</option>
                    <option ${param.language == 'Hindi'   ? 'selected' : ''}>Hindi</option>
                    <option ${param.language == 'Korean'  ? 'selected' : ''}>Korean</option>
                    <option ${param.language == 'French'  ? 'selected' : ''}>French</option>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn-search">Search</button>
            </div>
        </div>
    </form>

    <!-- Heading row -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <span style="color:#555; font-size:0.82rem; text-transform:uppercase; letter-spacing:2px;">
            <c:choose>
                <c:when test="${not empty searchLabel}">Results for: <span style="color:var(--gold)">${searchLabel}</span></c:when>
                <c:otherwise>All Movies</c:otherwise>
            </c:choose>
            &nbsp;&mdash;&nbsp; ${fn:length(movies)} found
        </span>
        <a href="movie?action=list" style="color:#555; font-size:0.8rem; text-decoration:none;">Clear filter</a>
    </div>

    <!-- Movie grid -->
    <c:choose>
        <c:when test="${empty movies}">
            <div class="empty-state">
                <h3>No Movies Found</h3>
                <p style="margin-top:0.5rem; font-size:0.9rem;">
                    Try a different search or
                    <a href="movie?action=adminForm" style="color:var(--gold)">add a movie</a>.
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4">
                <c:forEach var="movie" items="${movies}">
                    <div class="col">
                        <div class="movie-card">

                            <!-- Poster -->
                            <c:choose>
                                <c:when test="${not empty movie.imagePath}">
                                    <img src="${pageContext.request.contextPath}/movie-image/${movie.imagePath}"
                                         alt="${movie.title}">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-poster">
                                        <span style="font-size:2rem;">&#127916;</span>
                                        No Poster
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="card-body-inner">
                                <!-- Badges -->
                                <div class="d-flex gap-1 flex-wrap mb-2">
                                    <c:choose>
                                        <c:when test="${movie.type == 'ACTION'}"><span class="badge-type-action">Action</span></c:when>
                                        <c:when test="${movie.type == 'COMEDY'}"><span class="badge-type-comedy">Comedy</span></c:when>
                                        <c:otherwise><span class="badge-type-general">${movie.genre}</span></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${movie.status == 'Now Showing'}"><span class="badge-status-now">Now Showing</span></c:when>
                                        <c:when test="${movie.status == 'Coming Soon'}"><span class="badge-status-coming">Coming Soon</span></c:when>
                                        <c:otherwise><span class="badge-status-no">Ended</span></c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- Title & meta -->
                                <div class="movie-title">${movie.title}</div>
                                <div class="meta-text">${movie.language} &bull; ${movie.duration} min &bull; ${movie.rating}</div>

                                <!-- Action buttons -->
                                <div class="d-flex gap-2 mt-3 flex-wrap">
                                    <a href="movie?action=details&id=${movie.id}" class="btn-view">Details</a>
                                    <a href="movie?action=edit&id=${movie.id}"    class="btn-edit">Edit</a>
                                    <a href="movie?action=delete&id=${movie.id}"  class="btn-del"
                                       onclick="return confirm('Delete ${movie.title}?')">Delete</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
