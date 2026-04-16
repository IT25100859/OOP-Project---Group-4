<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CineBook – Movie Catalog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        :root {
            --gold: #f5c518;
            --dark: #0d0d0d;
            --card-bg: #1a1a2e;
        }
        body {
            background-color: var(--dark);
            color: #e0e0e0;
            font-family: 'Inter', sans-serif;
        }
        .hero {
            min-height: 100vh;
            background: linear-gradient(135deg, #0d0d0d 40%, #1a0a2e 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
        }
        .hero-title {
            font-family: 'Bebas Neue', sans-serif;
            font-size: clamp(4rem, 12vw, 9rem);
            letter-spacing: 0.05em;
            line-height: 1;
            color: white;
        }
        .hero-title span { color: var(--gold); }
        .hero-subtitle {
            font-size: 1.1rem;
            font-weight: 300;
            letter-spacing: 0.2em;
            text-transform: uppercase;
            color: #aaa;
            margin-top: 0.5rem;
        }
        .btn-gold {
            background-color: var(--gold);
            color: #000;
            font-weight: 600;
            padding: 0.75rem 2.5rem;
            border-radius: 3px;
            text-decoration: none;
            letter-spacing: 0.05em;
            transition: all 0.2s;
            border: none;
        }
        .btn-gold:hover { background-color: #d4a900; color: #000; }
        .btn-outline-light-custom {
            border: 1px solid #555;
            color: #ccc;
            padding: 0.75rem 2.5rem;
            border-radius: 3px;
            text-decoration: none;
            letter-spacing: 0.05em;
            transition: all 0.2s;
        }
        .btn-outline-light-custom:hover { border-color: var(--gold); color: var(--gold); }

        /* Floating film strip decoration */
        .film-strip {
            position: fixed;
            top: 0; left: 0;
            width: 32px;
            height: 100vh;
            background: repeating-linear-gradient(
                180deg, #111 0px, #111 18px, #000 18px, #000 36px
            );
            opacity: 0.4;
            z-index: 0;
        }
        .film-strip.right { left: auto; right: 0; }
    </style>
</head>
<body>
    <div class="film-strip"></div>
    <div class="film-strip right"></div>

    <div class="hero">
        <div style="position:relative; z-index:1;">
            <div class="hero-title">CINE<span>BOOK</span></div>
            <p class="hero-subtitle">Online Movie Reservation System</p>
            <p class="text-secondary mt-3 mb-4" style="font-size:0.95rem; max-width:460px; margin-inline:auto;">
                Browse the latest movies, filter by genre or language, and manage the catalog.
            </p>
            <div class="d-flex gap-3 justify-content-center flex-wrap mt-4">
                <a href="movie?action=list" class="btn-gold">Browse Movies</a>
                <a href="movie?action=adminForm" class="btn-outline-light-custom">Admin Panel</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
