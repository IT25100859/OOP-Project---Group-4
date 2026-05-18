<%@ page contentType="text/html;charset=UTF-8" %>
<style id="premium-global-theme">
    @import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Inter:wght@300;400;500;600;700&display=swap');

    :root {
        --bg: #fafbfc;
        --bg-grad: linear-gradient(135deg, #fafbfc 0%, #f1f3f7 100%);
        --surface: rgba(255, 255, 255, 0.9);
        --card: #ffffff;
        --border: rgba(0, 0, 0, 0.08);
        --border-md: rgba(0, 0, 0, 0.15);
        --primary: #d90429;
        --primary-glow: rgba(217, 4, 41, 0.12);
        --primary-bright: #ef233c;
        --text: #1a1c22;
        --text-invert: #ffffff;
        --muted: #667085;
        --dim: #9ca3af;
        --r: 16px;
        --glow: 0 0 25px var(--primary-glow);
    }

    /* Professional crisp aesthetic applied to the body */
    body {
        background: var(--bg-grad) !important;
        background-attachment: fixed !important;
        color: var(--text) !important;
        font-family: 'Inter', sans-serif !important;
        min-height: 100vh;
        overflow-x: hidden;
    }

    /* Very subtle ambient shadows for depth */
    body::after {
        content: '';
        position: fixed;
        width: 800px;
        height: 800px;
        background: radial-gradient(circle, rgba(217, 4, 41, 0.05) 0%, transparent 60%);
        top: -400px;
        left: -400px;
        border-radius: 50%;
        pointer-events: none;
        z-index: -1;
    }

    body::before {
        content: '';
        position: fixed;
        width: 600px;
        height: 600px;
        background: radial-gradient(circle, rgba(0,0,0,0.03) 0%, transparent 60%);
        bottom: -300px;
        right: -300px;
        border-radius: 50%;
        pointer-events: none;
        z-index: -1;
    }

    /* Navbar Redesign: Sleek Pitch Black with Red Accents */
    .navbar {
        background: rgba(15, 17, 21, 0.98) !important;
        backdrop-filter: blur(12px) !important;
        -webkit-backdrop-filter: blur(12px) !important;
        border-bottom: 2px solid var(--primary) !important;
        transition: all 0.3s ease;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    }

    .nav-brand, .navbar-brand {
        font-family: 'Bebas Neue', cursive !important;
        font-size: 2.2rem !important;
        letter-spacing: 4px !important;
        color: var(--primary) !important;
    }

    .nav-brand span, .navbar-brand span {
        color: var(--primary) !important;
    }

    .nav-link {
        font-weight: 500 !important;
        color: #a0aab8 !important;
        transition: all 0.3s ease !important;
        position: relative;
    }

    .nav-link:hover, .nav-link.active {
        color: var(--text-invert) !important;
    }

    .nav-link::after {
        content: '';
        position: absolute;
        bottom: 0px;
        left: 50%;
        width: 0;
        height: 2px;
        background: var(--primary);
        transition: width 0.3s ease, left 0.3s ease;
        border-radius: 2px;
    }

    .nav-link:hover::after, .nav-link.active::after {
        width: 100%;
        left: 0;
    }

    /* Clean Card Layouts */
    .card, .form-card, .stat-card, .table-card, .form-panel {
        background: var(--surface) !important;
        backdrop-filter: blur(20px) !important;
        -webkit-backdrop-filter: blur(20px) !important;
        border: 1px solid var(--border) !important;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.04) !important;
        border-radius: var(--r) !important;
        position: relative;
        overflow: hidden;
    }
    
    .cinema-panel {
        background: linear-gradient(160deg, #111 0%, #000 100%) !important;
        backdrop-filter: none !important;
        color: white !important;
    }
    
    .cinema-logo { color: var(--primary) !important; }

    /* Hover effects for actionable cards */
    .stat-card:hover, .featured-card:hover, .strip-card:hover {
        transform: translateY(-8px) !important;
        border-color: var(--border-md) !important;
        box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1), 0 0 0 2px var(--primary-glow) !important;
    }
    
    .strip-card, .featured-card {
        background: var(--card) !important;
        border: 1px solid var(--border) !important;
    }
    
    .strip-card .no-poster, .featured-card .no-poster {
        background: #f0f0f0 !important;
        color: var(--muted) !important;
    }
    
    .card-title {
        color: var(--text) !important;
        font-weight: 700 !important;
    }

    /* Buttons */
    .btn, .btn-gold, .btn-submit, .btn-signin {
        background: var(--primary) !important;
        color: var(--text-invert) !important;
        border: none !important;
        font-weight: 600 !important;
        letter-spacing: 0.5px !important;
        box-shadow: 0 5px 15px rgba(217, 4, 41, 0.3) !important;
        transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275) !important;
        text-transform: uppercase !important;
        cursor: pointer !important;
    }

    .btn:hover, .btn-gold:hover, .btn-submit:hover, .btn-signin:hover {
        transform: translateY(-3px) scale(1.02) !important;
        box-shadow: 0 10px 25px rgba(217, 4, 41, 0.45) !important;
        background: var(--primary-bright) !important;
    }

    .btn-outline, .btn-cancel {
        background: transparent !important;
        border: 2px solid var(--primary) !important;
        color: var(--primary) !important;
        font-weight: 600 !important;
    }

    .btn-outline:hover, .btn-cancel:hover {
        background: var(--primary-glow) !important;
        color: var(--primary) !important;
        transform: translateY(-2px) !important;
    }

    /* Headings */
    h1, h2, h3, .page-title, .hero-title, .section-title, .form-heading {
        font-family: 'Bebas Neue', cursive !important;
        letter-spacing: 2px !important;
        color: var(--text) !important;
    }

    h1 span, h2 span, h3 span, .page-title span, .hero-title span, .section-title span {
        color: var(--primary) !important;
    }
    
    .hero {
        background: transparent !important;
    }
    
    .hero-title {
        color: var(--text) !important;
    }

    /* Inputs & Selects */
    input[type="text"], input[type="password"], input[type="email"], input[type="number"], input[type="date"], input[type="time"], select, textarea, .input-box {
        background: #f8f9fa !important;
        border: 1px solid var(--border-md) !important;
        color: var(--text) !important;
        border-radius: 10px !important;
        transition: all 0.3s ease !important;
    }
    
    select option {
        background: #ffffff !important;
        color: #000000 !important;
    }

    input:focus, select:focus, textarea:focus, .input-box:focus-within {
        outline: none !important;
        border-color: var(--primary) !important;
        box-shadow: 0 0 0 4px var(--primary-glow) !important;
        background: #ffffff !important;
    }
    
    .input-icon, .input-toggle {
        color: var(--muted) !important;
    }

    /* Tables */
    table {
        border-collapse: separate !important;
        border-spacing: 0 4px !important;
        background: transparent !important;
    }

    thead th {
        background: #f1f3f7 !important;
        color: var(--muted) !important;
        border: none !important;
        padding: 15px 20px !important;
        font-weight: 700 !important;
        letter-spacing: 1px !important;
        text-transform: uppercase !important;
    }

    tbody tr {
        background: #ffffff !important;
        box-shadow: 0 2px 5px rgba(0,0,0,0.02) !important;
        transition: transform 0.2s, box-shadow 0.2s !important;
        border: 1px solid var(--border) !important;
    }

    tbody tr:hover {
        transform: translateY(-2px) !important;
        box-shadow: 0 8px 20px rgba(0,0,0,0.06) !important;
        z-index: 2;
        position: relative;
    }

    td {
        padding: 15px 20px !important;
        border-top: 1px solid var(--border) !important;
        border-bottom: 1px solid var(--border) !important;
    }

    td:first-child {
        border-left: 1px solid var(--border) !important;
        border-radius: 8px 0 0 8px !important;
    }

    td:last-child {
        border-right: 1px solid var(--border) !important;
        border-radius: 0 8px 8px 0 !important;
    }
    
    .admin-chip, .badge-now, .badge-coming, .badge-old, .b-ok, .b-cancel {
        font-family: 'Inter', sans-serif !important;
        font-weight: 600 !important;
    }
    
    .b-ok, .badge-now {
        background: rgba(16, 185, 129, 0.15) !important;
        color: #059669 !important;
        border: 1px solid #10b981 !important;
    }
    
    .b-cancel, .badge-old {
        background: rgba(102, 112, 133, 0.15) !important;
        color: #475467 !important;
        border: 1px solid #98a2b3 !important;
    }
    
    .badge-coming {
        background: var(--primary-glow) !important;
        color: var(--primary) !important;
        border: 1px solid var(--primary) !important;
    }
    
    .amount-cell, .stat-num {
        color: var(--text) !important;
    }

    /* Scrollbars */
    ::-webkit-scrollbar {
        width: 8px;
        height: 8px;
    }
    ::-webkit-scrollbar-track {
        background: var(--bg);
    }
    ::-webkit-scrollbar-thumb {
        background: var(--dim);
        border-radius: 4px;
    }
    ::-webkit-scrollbar-thumb:hover {
        background: var(--primary);
    }

    /* =========================================================
       SPECIFIC BOOKING SYSTEM UI OVERRIDES (White, Black, Red)
       ========================================================= */

    /* Hero Backpack & Gradients (details.jsp, index.jsp) */
    .hero-bg, .cinema-panel {
        filter: blur(8px) brightness(0.9) saturate(1.2) !important;
        background-color: var(--bg) !important;
    }
    .hero-bg-gradient {
        background: linear-gradient(to bottom, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0.7) 50%, rgba(255,255,255,1) 100%) !important;
    }
    .hero-title, .show-title {
        color: var(--text) !important;
        text-shadow: 0 2px 4px rgba(0,0,0,0.1) !important;
    }
    
    /* Pills & Status Dots */
    .pill {
        background: rgba(0,0,0,0.05) !important;
        border: 1px solid rgba(0,0,0,0.1) !important;
        color: var(--text) !important;
        font-weight: 600 !important;
        box-shadow: 0 2px 4px rgba(0,0,0,0.02) !important;
    }
    .pill-gold {
        background: rgba(217,4,41,0.1) !important;
        border-color: rgba(217,4,41,0.3) !important;
        color: var(--primary) !important;
    }
    .pill-violet, .badge-coming {
        background: #111111 !important;
        border-color: #333 !important;
        color: white !important;
    }
    .pill-green {
        background: rgba(0,0,0,0.05) !important;
        border-color: rgba(0,0,0,0.1) !important;
        color: #111 !important;
    }
    .status-dot.dot-green { background: var(--primary) !important; box-shadow: 0 0 8px var(--primary-glow) !important; }
    .status-dot.dot-violet { background: #111 !important; box-shadow: 0 0 8px rgba(0,0,0,0.2) !important; }
    .status-text { color: var(--text) !important; font-weight: 600 !important; }

    /* The Seat Grid (hall-layout.jsp) - Total Transformation */
    .seat {
        border-radius: 6px 6px 3px 3px !important;
        font-weight: 700 !important;
        transition: transform 0.2s cubic-bezier(0.175, 0.885, 0.32, 1.275), box-shadow 0.2s, background 0.2s !important;
        box-shadow: 0 2px 4px rgba(0,0,0,0.05) !important;
        display: flex !important;
        align-items: center !important;
        justify-content: center !important;
    }
    .seat:hover {
        transform: scale(1.2) translateY(-2px) !important;
        z-index: 5 !important;
    }
    .seat.std {
        background: #ffffff !important;
        color: var(--muted) !important;
        border: 1px solid rgba(0,0,0,0.15) !important;
    }
    .seat.std:hover {
        background: #ffffff !important;
        border-color: var(--primary) !important;
        color: var(--primary) !important;
        box-shadow: 0 6px 12px rgba(217,4,41,0.2) !important;
    }
    .seat.vip {
        background: #121418 !important;
        color: #ffffff !important;
        border: 1px solid #333 !important;
    }
    .seat.vip:hover {
        background: #000000 !important;
        border-color: var(--primary) !important;
        box-shadow: 0 6px 12px rgba(0,0,0,0.3) !important;
        color: #ffffff !important;
    }
    .seat.booked {
        background: #f1f3f7 !important;
        color: #d1d5db !important;
        border: 1px dashed #d1d5db !important;
        opacity: 0.6 !important;
        box-shadow: none !important;
    }
    .seat.booked:hover {
        transform: none !important;
        z-index: 1 !important;
    }
    .seat.selected {
        background: var(--primary) !important;
        color: white !important;
        border: none !important;
        box-shadow: 0 4px 15px rgba(217,4,41,0.4) !important;
        transform: scale(1.15) translateY(-2px) !important;
    }
    
    /* Seat Legends */
    .legend-box { border-radius: 4px 4px 2px 2px !important; box-shadow: 0 2px 5px rgba(0,0,0,0.05) !important; }
    
    /* Show Banner & Booking Summary */
    .show-banner, .summary-panel {
        background: var(--card) !important;
        border: 1px solid var(--border-md) !important;
        box-shadow: 0 8px 25px rgba(0,0,0,0.06) !important;
    }
    .summary-panel.active {
        border-color: var(--primary) !important;
        box-shadow: 0 10px 30px rgba(217,4,41,0.15) !important;
    }
    .price-value, .summary-total {
        color: var(--primary) !important;
        text-shadow: 0 2px 5px rgba(217,4,41,0.1) !important;
    }
    .show-meta span, .price-label, .summary-breakdown {
        color: var(--muted) !important;
        font-weight: 500 !important;
    }
    .seat-chip-vip {
        background: #111 !important;
        border: 1px solid #333 !important;
        color: white !important;
        font-weight: 600 !important;
    }
    .seat-chip-std {
        background: rgba(217,4,41,0.08) !important;
        border: 1px solid rgba(217,4,41,0.2) !important;
        color: var(--primary) !important;
        font-weight: 600 !important;
    }
    .vip-notice {
        background: #111 !important;
        border: 1px solid #333 !important;
        color: #fff !important;
        font-weight: 500 !important;
        letter-spacing: 0.5px !important;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1) !important;
    }
    .vip-notice i { color: var(--primary) !important; }
    .screen-bar {
        background: linear-gradient(to right, transparent, rgba(0,0,0,0.2), transparent) !important;
    }
    
    /* Row/Column Labels */
    .row-label { color: var(--text) !important; font-weight: 700 !important; margin-right: 5px !important; }

</style>
