<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Movie – CineBook Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        :root { --gold: #f5c518; --dark: #0d0d0d; }
        body { background: var(--dark); color: #e0e0e0; font-family: 'Inter', sans-serif; min-height: 100vh; }
        .navbar-brand { font-family: 'Bebas Neue'; font-size: 1.6rem; color: var(--gold) !important; letter-spacing: 2px; }
        .nav-link { color: #ccc !important; }
        .form-card { background: #16213e; border: 1px solid #1f2d4e; border-radius: 12px; padding: 2.5rem; }
        .page-title { font-family: 'Bebas Neue'; font-size: 2.5rem; color: white; letter-spacing: 2px; }
        .movie-id-badge {
            background: #0d0d0d; border: 1px solid #333; border-radius: 4px;
            padding: 3px 10px; font-size: 0.78rem; color: #666; font-family: monospace;
        }
        .form-label { color: #aaa; font-size: 0.85rem; font-weight: 600; letter-spacing: 0.05em; text-transform: uppercase; }
        .form-control, .form-select {
            background: #0d0d0d; color: #e0e0e0; border: 1px solid #333; border-radius: 6px;
        }
        .form-control:focus, .form-select:focus {
            background: #0d0d0d; color: #e0e0e0; border-color: var(--gold);
            box-shadow: 0 0 0 2px rgba(245,197,24,0.15);
        }
        .form-select option { background: #0d0d0d; }
        .section-divider { border-color: #1f2d4e; margin: 2rem 0; }
        .extra-field { display: none; }
        .extra-field.visible { display: block; }
        .btn-submit { background: var(--gold); color: #000; font-weight: 700; border: none; padding: 0.75rem 2.5rem; border-radius: 6px; }
        .btn-submit:hover { background: #d4a900; }
        .btn-back { background: #1f2d4e; color: #aaa; border: none; padding: 0.75rem 1.5rem; border-radius: 6px; text-decoration: none; }
        .hint-text { font-size: 0.78rem; color: #555; margin-top: 3px; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg" style="background:#0d0d0d; border-bottom:1px solid #222;">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">CINEBOOK</a>
        <div class="d-flex gap-3 ms-auto">
            <a href="movie?action=list" class="nav-link">Gallery</a>
        </div>
    </div>
</nav>

<div class="container py-5" style="max-width: 700px;">

    <div class="d-flex align-items-center gap-3 mb-4 flex-wrap">
        <a href="movie?action=list" class="btn-back">← Back</a>
        <div class="page-title">Edit Movie</div>
        <span class="movie-id-badge">${movie.id}</span>
    </div>

    <c:if test="${empty movie}">
        <div class="alert alert-danger">Movie not found.</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mb-4">${error}</div>
    </c:if>

    <c:if test="${not empty movie}">
    <div class="form-card">
        <form action="movie" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id"     value="${movie.id}">

            <!-- ── SECTION 1: Basic Info ───────────────────────────── -->
            <h6 style="color:var(--gold); letter-spacing:2px; font-size:0.75rem; text-transform:uppercase; margin-bottom:1.25rem;">
                Basic Information
            </h6>

            <div class="mb-3">
                <label class="form-label">Movie Title *</label>
                <input type="text" name="title" class="form-control" required value="${movie.title}">
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Genre *</label>
                    <select name="genre" class="form-select" required>
                        <c:forEach var="g" items="${['Action','Comedy','Drama','Horror','Thriller','Romance','Sci-Fi','Animation']}">
                            <option ${movie.genre == g ? 'selected' : ''}>${g}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Duration (minutes) *</label>
                    <input type="number" name="duration" class="form-control" required min="1" max="500" value="${movie.duration}">
                </div>
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Language *</label>
                    <select name="language" class="form-select" required>
                        <c:forEach var="l" items="${['English','Sinhala','Tamil','Hindi','Korean','French']}">
                            <option ${movie.language == l ? 'selected' : ''}>${l}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Age Rating *</label>
                    <select name="rating" class="form-select" required>
                        <c:forEach var="r" items="${['G','PG','PG-13','R']}">
                            <option ${movie.rating == r ? 'selected' : ''}>${r}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Status *</label>
                <select name="status" class="form-select" required>
                    <c:forEach var="s" items="${['Now Showing','Coming Soon','No Longer Showing']}">
                        <option ${movie.status == s ? 'selected' : ''}>${s}</option>
                    </c:forEach>
                </select>
            </div>

            <hr class="section-divider">

            <!-- ── SECTION 2: Movie Type ──────────────────────────── -->
            <h6 style="color:var(--gold); letter-spacing:2px; font-size:0.75rem; text-transform:uppercase; margin-bottom:1.25rem;">
                Movie Type
            </h6>

            <div class="mb-3">
                <label class="form-label">Movie Type *</label>
                <select name="type" id="movieType" class="form-select" required onchange="handleTypeChange(this.value)">
                    <option value="ACTION" ${movie.type == 'ACTION' ? 'selected' : ''}>Action Movie</option>
                    <option value="COMEDY" ${movie.type == 'COMEDY' ? 'selected' : ''}>Comedy Movie</option>
                    <option value="GENERAL" ${movie.type == 'GENERAL' ? 'selected' : ''}>General Movie</option>
                </select>
            </div>

            <!-- Action extra field -->
            <div class="mb-3 extra-field" id="fieldAction">
                <label class="form-label">Action Intensity</label>
                <select name="extra" id="extraAction" class="form-select">
                    <c:forEach var="i" items="${['Low','Medium','High','Extreme']}">
                        <option ${movie.extraAttribute == i ? 'selected' : ''}>${i}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Comedy extra field -->
            <div class="mb-3 extra-field" id="fieldComedy">
                <label class="form-label">Humor Style</label>
                <select name="extra" id="extraComedy" class="form-select">
                    <c:forEach var="h" items="${['Slapstick','Satire','Romantic','Dark']}">
                        <option ${movie.extraAttribute == h ? 'selected' : ''}>${h}</option>
                    </c:forEach>
                </select>
            </div>

            <hr class="section-divider">

            <!-- ── Buttons ─────────────────────────────────────────── -->
            <div class="d-flex gap-3">
                <button type="submit" class="btn-submit">Save Changes</button>
                <a href="movie?action=details&id=${movie.id}" class="btn-back">Cancel</a>
            </div>
        </form>
    </div>
    </c:if>
</div>

<script>
    // Show/hide subclass-specific field based on selected type
    function handleTypeChange(type) {
        document.getElementById('fieldAction').classList.remove('visible');
        document.getElementById('fieldComedy').classList.remove('visible');
        document.getElementById('extraAction').disabled = true;
        document.getElementById('extraComedy').disabled = true;

        if (type === 'ACTION') {
            document.getElementById('fieldAction').classList.add('visible');
            document.getElementById('extraAction').disabled = false;
        } else if (type === 'COMEDY') {
            document.getElementById('fieldComedy').classList.add('visible');
            document.getElementById('extraComedy').disabled = false;
        }
    }

    // Run on page load to show correct field for existing type
    window.onload = function() {
        const type = document.getElementById('movieType').value;
        handleTypeChange(type);
    };
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
