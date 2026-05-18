<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirm Booking – CineBook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root{--bg:#07080d;--surface:#0d1117;--card:#0f1520;--border:#1a2035;--border-md:#252f45;--gold:#f5c518;--gold-dim:rgba(245,197,24,.12);--violet:#8b5cf6;--emerald:#10b981;--text:#e8edf5;--muted:#8892a4;--dim:#3d4557;--r:12px}
        *{box-sizing:border-box;margin:0;padding:0}
        body{background:var(--bg);color:var(--text);font-family:'Poppins',sans-serif;min-height:100vh}
        body::before{content:'';position:fixed;inset:0;background:radial-gradient(ellipse 60% 40% at 50% -5%,rgba(245,197,24,.05),transparent 55%);pointer-events:none}

        .navbar{background:rgba(7,8,13,.85);backdrop-filter:blur(20px);border-bottom:1px solid var(--border);padding:0 2rem;height:64px;display:flex;align-items:center;justify-content:space-between;position:sticky;top:0;z-index:100}
        .nav-brand{font-family:'Bebas Neue';font-size:1.75rem;letter-spacing:4px;color:var(--gold);text-decoration:none;display:flex;align-items:center;gap:8px}
        .nav-link{color:var(--muted);font-size:.82rem;text-decoration:none;padding:6px 12px;border-radius:8px;transition:color .2s,background .2s}
        .nav-link:hover{color:var(--text);background:rgba(255,255,255,.05)}
        .nav-username{color:var(--gold);font-size:.82rem;font-weight:600}

        .page{max-width:720px;margin:0 auto;padding:2rem;position:relative;z-index:1}

        /* Steps */
        .steps{display:flex;gap:0;margin-bottom:2rem;border-radius:var(--r);overflow:hidden;border:1px solid var(--border)}
        .step{flex:1;text-align:center;padding:.6rem;font-size:.68rem;font-weight:600;letter-spacing:.8px;text-transform:uppercase;color:var(--dim);border-right:1px solid var(--border);background:var(--surface)}
        .step:last-child{border-right:none}
        .step.done{color:var(--emerald)}
        .step.active{color:var(--gold);background:rgba(245,197,24,.05)}

        /* Show card */
        .show-card{background:var(--surface);border:1px solid var(--border);border-radius:var(--r);padding:1.4rem;margin-bottom:1.25rem}
        .card-label{color:var(--dim);font-size:.68rem;font-weight:700;text-transform:uppercase;letter-spacing:1.2px;margin-bottom:.9rem;display:flex;align-items:center;gap:7px}
        .card-label::before{content:'';width:3px;height:13px;background:var(--gold);border-radius:2px;display:block}
        .show-row{display:flex;justify-content:space-between;padding:.45rem 0;border-bottom:1px solid var(--border);font-size:.88rem}
        .show-row:last-child{border:none}
        .show-key{color:var(--muted)}
        .show-val{color:var(--text);font-weight:500;text-align:right}

        /* Seat pills */
        .seat-pills{display:flex;flex-wrap:wrap;gap:6px;margin-top:.75rem}
        .seat-pill-vip{background:rgba(245,197,24,.12);border:1px solid rgba(245,197,24,.3);color:var(--gold);font-size:.72rem;padding:4px 10px;border-radius:6px;font-weight:600}
        .seat-pill-std{background:rgba(16,185,129,.1);border:1px solid rgba(16,185,129,.25);color:#34d399;font-size:.72rem;padding:4px 10px;border-radius:6px;font-weight:600}
        .vip-note{color:var(--dim);font-size:.72rem;margin-top:.5rem;display:flex;align-items:center;gap:5px}

        /* Price summary */
        .price-card{background:var(--surface);border:1px solid var(--border);border-radius:var(--r);padding:1.4rem;margin-bottom:1.25rem}
        .price-row{display:flex;justify-content:space-between;padding:.35rem 0;font-size:.88rem}
        .price-label{color:var(--muted)}
        .price-total-row{border-top:2px solid var(--border-md);margin-top:.6rem;padding-top:.75rem;font-size:1.2rem;font-weight:700;display:flex;justify-content:space-between;color:var(--gold)}

        /* Buttons */
        .btn-confirm{width:100%;padding:.85rem;background:var(--gold);color:#000;font-weight:700;font-size:1rem;border:none;border-radius:var(--r);cursor:pointer;display:flex;align-items:center;justify-content:center;gap:8px;transition:background .15s,transform .1s;font-family:'Poppins'}
        .btn-confirm:hover{background:#e0b000;transform:translateY(-1px)}
        .btn-back{width:100%;padding:.8rem;background:rgba(255,255,255,.04);color:var(--muted);font-size:.9rem;border:1px solid var(--border);border-radius:var(--r);cursor:pointer;display:flex;align-items:center;justify-content:center;gap:8px;transition:background .15s;text-decoration:none;font-family:'Poppins';margin-top:.75rem}
        .btn-back:hover{background:rgba(255,255,255,.07);color:var(--text)}

        .type-badge{font-size:.72rem;padding:3px 10px;border-radius:20px}
        .type-2d{background:rgba(59,130,246,.15);color:#93c5fd;border:1px solid rgba(59,130,246,.25)}
        .type-3d{background:rgba(139,92,246,.15);color:#a78bfa;border:1px solid rgba(139,92,246,.25)}
        .type-imax{background:rgba(244,63,94,.12);color:#fca5a5;border:1px solid rgba(244,63,94,.2)}
    </style>

    <jsp:include page="/WEB-INF/views/premium-theme.jsp" />
</head>
<body>
<nav class="navbar">
    <a class="nav-brand" href="${pageContext.request.contextPath}/"><i class="bi bi-film"></i> CINEBOOK</a>
    <div style="display:flex;align-items:center;gap:6px">
        <a href="${pageContext.request.contextPath}/" class="nav-link">Gallery</a>
        <a href="${pageContext.request.contextPath}/booking/my-bookings" class="nav-link">My Tickets</a>
        <c:if test="${not empty sessionScope.username}">
            <span class="nav-username">${sessionScope.username}</span>
            <a href="${pageContext.request.contextPath}/user/logout" class="nav-link">Logout</a>
        </c:if>
    </div>
</nav>

<div class="page">
    <div class="steps">
        <div class="step done"><i class="bi bi-check-circle-fill"></i> Select Seats</div>
        <div class="step active"><i class="bi bi-clipboard-check"></i> Confirm</div>
        <div class="step">Payment</div>
        <div class="step">Receipt</div>
    </div>

    <!-- Show details -->
    <div class="show-card">
        <div class="card-label"><i class="bi bi-camera-reels"></i> Show Details</div>
        <div class="show-row"><span class="show-key">Movie</span><span class="show-val" style="font-weight:700">${showtime.movieTitle}</span></div>
        <div class="show-row"><span class="show-key">Hall</span><span class="show-val">${showtime.hallName}</span></div>
        <div class="show-row"><span class="show-key">Date &amp; Time</span><span class="show-val">${showtime.showDate} &nbsp; ${showtime.showTime}</span></div>
        <div class="show-row" style="border:none"><span class="show-key">Show Type</span>
            <span class="show-val">
                <c:choose>
                    <c:when test="${showtime.showType=='STANDARD_2D'}"><span class="type-badge type-2d">Standard 2D</span></c:when>
                    <c:when test="${showtime.showType=='PREMIUM_3D'}"><span class="type-badge type-3d">Premium 3D</span></c:when>
                    <c:otherwise><span class="type-badge type-imax">IMAX</span></c:otherwise>
                </c:choose>
            </span>
        </div>
    </div>

    <!-- Selected seats -->
    <div class="show-card">
        <div class="card-label"><i class="bi bi-grid-3x3-gap"></i> Selected Seats (${count})</div>
        <div class="seat-pills">
            <c:forEach var="sd" items="${seatDetails}">
                <c:choose>
                    <c:when test="${sd.type=='VIP'}"><span class="seat-pill-vip">&#9733; ${sd.seat} — LKR ${sd.price}</span></c:when>
                    <c:otherwise><span class="seat-pill-std">${sd.seat} — LKR ${sd.price}</span></c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <div class="vip-note"><i class="bi bi-info-circle"></i> Rows A &amp; B are VIP (1.5× base). Base show price: LKR ${showTypePrice}/seat</div>
    </div>

    <!-- Price breakdown -->
    <div class="price-card">
        <div class="card-label"><i class="bi bi-receipt"></i> Price Summary</div>
        <c:if test="${stdCount>0}">
            <div class="price-row"><span class="price-label">Standard seats (${stdCount})</span><span>LKR ${showTypePrice} × ${stdCount}</span></div>
        </c:if>
        <c:if test="${vipCount>0}">
            <div class="price-row"><span class="price-label" style="color:var(--gold)">&#9733; VIP seats (${vipCount}) × 1.5</span><span style="color:var(--gold)">LKR ${showTypePrice} × 1.5 × ${vipCount}</span></div>
        </c:if>
        <div class="price-total-row"><span>Total</span><span>LKR ${totalPrice}</span></div>
    </div>

    <!-- Action -->
    <form action="${pageContext.request.contextPath}/booking/confirm" method="post">
        <input type="hidden" name="showtimeId" value="${showtime.showtimeId}">
        <input type="hidden" name="seats"      value="${seats}">
        <button type="submit" class="btn-confirm">
            <i class="bi bi-ticket-perforated-fill"></i> Confirm Booking — LKR ${totalPrice}
        </button>
    </form>
    <a href="javascript:history.back()" class="btn-back">&#8592; Go Back &amp; Change Seats</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
