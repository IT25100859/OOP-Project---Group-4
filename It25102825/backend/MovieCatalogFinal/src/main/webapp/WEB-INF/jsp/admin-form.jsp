<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Movie – CineBook Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        :root { --gold: #f5c518; --dark: #0d0d0d; }
        body { background: var(--dark); color: #e0e0e0; font-family: 'Inter', sans-serif; min-height: 100vh; }
        .navbar { background: rgba(13,13,13,0.97); border-bottom: 1px solid #1a1a1a; padding: 0.75rem 2rem; }
        .navbar-brand { font-family: 'Bebas Neue'; font-size: 1.8rem; color: var(--gold) !important; letter-spacing: 3px; text-decoration: none; }
        .nav-link { color: #aaa !important; font-size: 0.85rem; text-decoration: none; }
        .nav-link:hover { color: var(--gold) !important; }

        .form-card { background: #16213e; border: 1px solid #1f2d4e; border-radius: 12px; padding: 2.5rem; }
        .page-title { font-family: 'Bebas Neue'; font-size: 2.5rem; color: white; letter-spacing: 2px; }
        .section-heading { color: var(--gold); letter-spacing: 2px; font-size: 0.72rem; text-transform: uppercase; margin-bottom: 1.25rem; display: block; }
        .form-label { color: #888; font-size: 0.82rem; font-weight: 600; letter-spacing: 0.05em; text-transform: uppercase; }
        .form-control, .form-select { background: #0d0d0d; color: #e0e0e0; border: 1px solid #333; border-radius: 6px; }
        .form-control:focus, .form-select:focus { background: #0d0d0d; color: #e0e0e0; border-color: var(--gold); box-shadow: none; }
        .form-select option { background: #0d0d0d; }
        .hint-text { font-size: 0.75rem; color: #555; margin-top: 4px; }
        hr.divider { border-color: #1f2d4e; margin: 2rem 0; }
        .extra-field { display: none; }
        .extra-field.visible { display: block; }
        .btn-submit { background: var(--gold); color: #000; font-weight: 700; border: none; padding: 0.75rem 2.5rem; border-radius: 6px; font-size: 1rem; }
        .btn-submit:hover { background: #d4a900; }
        .btn-cancel { background: #1f2d4e; color: #aaa; border: none; padding: 0.75rem 1.5rem; border-radius: 6px; text-decoration: none; display: inline-block; }
        .btn-cancel:hover { background: #2a3f6e; color: white; }
        .preview-img { max-width: 200px; border-radius: 8px; border: 1px solid #333; margin-top: 10px; display: none; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar d-flex align-items-center">
    <a class="navbar-brand me-auto" href="/">CINEBOOK</a>
    <div class="d-flex gap-4">
        <a href="movie?action=list" class="nav-link">Gallery</a>
    </div>
</nav>

<div class="container py-5" style="max-width: 720px;">

    <div class="d-flex align-items-center gap-3 mb-4">
        <a href="movie?action=list" class="btn-cancel">&#8592; Back</a>
        <div class="page-title">Add New Movie</div>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mb-4">${error}</div>
    </c:if>

    <div class="form-card">
        <!-- enctype required for image upload -->
        <form action="movie" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">

            <!-- SECTION 1: Basic Info -->
            <span class="section-heading">Basic Information</span>

            <div class="mb-3">
                <label class="form-label">Movie Title *</label>
                <input type="text" name="title" class="form-control" required placeholder="e.g. Inception">
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Genre *</label>
                    <select name="genre" class="form-select" required>
                        <option value="">Select Genre</option>
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
                <div class="col-md-6">
                    <label class="form-label">Duration (minutes) *</label>
                    <input type="number" name="duration" class="form-control" required min="1" max="500" placeholder="e.g. 120">
                </div>
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label">Language *</label>
                    <select name="language" class="form-select" required>
                        <option value="">Select Language</option>
                        <option>English</option>
                        <option>Sinhala</option>
                        <option>Tamil</option>
                        <option>Hindi</option>
                        <option>Korean</option>
                        <option>French</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Age Rating *</label>
                    <select name="rating" class="form-select" required>
                        <option value="">Select Rating</option>
                        <option>G</option>
                        <option>PG</option>
                        <option>PG-13</option>
                        <option>R</option>
                    </select>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Status *</label>
                <select name="status" class="form-select" required>
                    <option value="">Select Status</option>
                    <option>Now Showing</option>
                    <option>Coming Soon</option>
                    <option>No Longer Showing</option>
                </select>
            </div>

            <hr class="divider">

            <!-- SECTION 2: Movie Type -->
            <span class="section-heading">Movie Type (OOP Classification)</span>

            <div class="mb-3">
                <label class="form-label">Movie Type *</label>
                <select name="type" id="movieType" class="form-select" required onchange="handleTypeChange(this.value)">
                    <option value="">Select Type</option>
                    <option value="ACTION">Action Movie (ActionMovie subclass)</option>
                    <option value="COMEDY">Comedy Movie (ComedyMovie subclass)</option>
                    <option value="GENERAL">General Movie (GeneralMovie subclass)</option>
                </select>
                <div class="hint-text">Determines which Java subclass is instantiated.</div>
            </div>

            <!-- Action-specific field -->
            <div class="mb-3 extra-field" id="fieldAction">
                <label class="form-label">Action Intensity</label>
                <select name="extra" id="extraAction" class="form-select" disabled>
                    <option value="">Select Intensity</option>
                    <option>Low</option>
                    <option>Medium</option>
                    <option>High</option>
                    <option>Extreme</option>
                </select>
                <div class="hint-text">Stored as actionIntensity in ActionMovie class.</div>
            </div>

            <!-- Comedy-specific field -->
            <div class="mb-3 extra-field" id="fieldComedy">
                <label class="form-label">Humor Style</label>
                <select name="extra" id="extraComedy" class="form-select" disabled>
                    <option value="">Select Style</option>
                    <option>Slapstick</option>
                    <option>Satire</option>
                    <option>Romantic</option>
                    <option>Dark</option>
                </select>
                <div class="hint-text">Stored as humorStyle in ComedyMovie class.</div>
            </div>

            <hr class="divider">

            <!-- SECTION 3: Movie Poster -->
            <span class="section-heading">Movie Poster (optional)</span>

            <div class="mb-3">
                <label class="form-label">Upload Image</label>
                <input type="file" name="image" id="imageInput" class="form-control"
                       accept="image/*" onchange="previewImage(this)">
                <div class="hint-text">JPG, PNG or WEBP — max 5 MB</div>
            </div>
            <img id="previewImg" class="preview-img" src="#" alt="Preview">

            <hr class="divider">

            <!-- Buttons -->
            <div class="d-flex gap-3">
                <button type="submit" class="btn-submit">Add Movie</button>
                <a href="movie?action=list" class="btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
</div>

<script>
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

    function previewImage(input) {
        const img = document.getElementById('previewImg');
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = e => {
                img.src = e.target.result;
                img.style.display = 'block';
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
