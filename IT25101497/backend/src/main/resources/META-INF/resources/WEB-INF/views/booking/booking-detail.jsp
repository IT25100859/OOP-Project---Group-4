<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} – CineBook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Libre+Barcode+39&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root{--bg:#07080d;--surface:#0d1117;--card:#0f1520;--border:#1a2035;--border-md:#252f45;--gold:#f5c518;--gold-dim:rgba(245,197,24,.12);--violet:#8b5cf6;--emerald:#10b981;--rose:#f43f5e;--text:#e8edf5;--muted:#8892a4;--dim:#3d4557;--r:12px}
        *{box-sizing:border-box;margin:0;padding:0}
        body{background:var(--bg);color:var(--text);font-family:'Poppins',sans-serif;min-height:100vh}
        body::before{content:'';position:fixed;inset:0;background:radial-gradient(ellipse 70% 50% at 50% 0%,rgba(245,197,24,.04),transparent 55%);pointer-events:none}

        .navbar{position:sticky;top:0;z-index:100;background:rgba(7,8,13,.85);backdrop-filter:blur(20px);border-bottom:1px solid var(--border);padding:0 2rem;height:64px;display:flex;align-items:center;justify-content:space-between}
        .nav-brand{font-family:'Bebas Neue';font-size:1.75rem;letter-spacing:4px;color:var(--gold);text-decoration:none;display:flex;align-items:center;gap:8px}
        .nav-link{color:var(--muted);font-size:.82rem;text-decoration:none;padding:6px 12px;border-radius:8px;transition:color .2s,background .2s}
        .nav-link:hover{color:var(--text);background:rgba(255,255,255,.05)}
        .nav-username{color:var(--gold);font-size:.82rem;font-weight:600}

        .page{position:relative;z-index:1;max-width:640px;margin:0 auto;padding:2rem}

        /* Ticket */
        .ticket{background:var(--surface);border:1px solid var(--border);border-radius:var(--r);overflow:hidden;box-shadow:0 20px 60px rgba(0,0,0,.5)}

        /* Header */
        .t-header{background:linear-gradient(135deg,#0a0f20,#170a30);padding:1.75rem 2rem;position:relative}
        .t-header::after{content:'';display:block;height:2px;background:repeating-linear-gradient(90deg,var(--border) 0,var(--border) 10px,transparent 10px,transparent 20px);margin-top:1.25rem}
        .t-bid{font-family:monospace;color:var(--dim);font-size:.7rem;margin-bottom:.3rem}
        .t-movie{font-family:'Bebas Neue';font-size:2.2rem;letter-spacing:2px;color:#fff;line-height:1}
        .t-stamp-ok{display:inline-block;border:2px solid var(--emerald);color:var(--emerald);font-family:'Bebas Neue';font-size:.9rem;letter-spacing:3px;padding:2px 10px;border-radius:4px;transform:rotate(-4deg);margin-top:.6rem}
        .t-stamp-cancel{display:inline-block;border:2px solid var(--dim);color:var(--dim);font-family:'Bebas Neue';font-size:.9rem;letter-spacing:3px;padding:2px 10px;border-radius:4px;transform:rotate(-4deg);margin-top:.6rem}

        /* Body */
        .t-body{padding:1.5rem 2rem}
        .t-row{display:flex;justify-content:space-between;padding:.5rem 0;border-bottom:1px solid var(--border);font-size:.88rem}
        .t-row:last-of-type{border:none}
        .t-key{color:var(--muted);font-size:.7rem;font-weight:600;text-transform:uppercase;letter-spacing:.8px}
        .t-val{color:var(--text);text-align:right;font-weight:500}

        /* Seat table */
        .seat-table-wrap{background:var(--card);border:1px solid var(--border);border-radius:8px;padding:1rem;margin-top:1rem}
        .st-label{color:var(--dim);font-size:.68rem;font-weight:700;text-transform:uppercase;letter-spacing:1px;margin-bottom:.65rem}
        .seat-row-item{display:flex;justify-content:space-between;padding:.3rem 0;font-size:.85rem;border-bottom:1px solid var(--border)}
        .seat-row-item:last-child{border:none}
        .pill-vip{background:rgba(245,197,24,.12);border:1px solid rgba(245,197,24,.3);color:var(--gold);font-size:.68rem;padding:2px 8px;border-radius:4px}
        .pill-std{background:rgba(16,185,129,.1);border:1px solid rgba(16,185,129,.25);color:#34d399;font-size:.68rem;padding:2px 8px;border-radius:4px}

        /* Footer */
        .t-footer{background:var(--card);border-top:2px dashed var(--border);padding:1.1rem 2rem;display:flex;align-items:center;justify-content:space-between}
        .barcode{font-family:'Libre Barcode 39',monospace;font-size:2.2rem;color:var(--dim);letter-spacing:2px}
        .total-block{text-align:right}
        .total-label{color:var(--dim);font-size:.68rem;text-transform:uppercase;letter-spacing:1px}
        .total-value{color:var(--gold);font-family:'Bebas Neue';font-size:1.8rem;letter-spacing:1px}

        /* Actions */
        .actions{display:flex;gap:.75rem;justify-content:flex-end;margin-top:1.25rem;flex-wrap:wrap}
        .btn-act{padding:9px 20px;border-radius:9px;font-size:.85rem;font-weight:600;text-decoration:none;border:none;cursor:pointer;display:inline-flex;align-items:center;gap:6px;transition:filter .15s,transform .1s;font-family:'Poppins'}
        .btn-act:hover{filter:brightness(1.1);transform:translateY(-1px)}
        .btn-back{background:rgba(255,255,255,.05);color:var(--muted);border:1px solid var(--border)}
        .btn-cancel-bk{background:rgba(244,63,94,.1);color:#fb7185;border:1px solid rgba(244,63,94,.25)}
    </style>

    <jsp:include page="/WEB-INF/views/premium-theme.jsp" />
</head>
<body>
<nav class="navbar">
    <a class="nav-brand" href="${pageContext.request.contextPath}/"><i class="bi bi-film"></i> CINEBOOK</a>
    <div style="display:flex;align-items:center;gap:6px">
        <a href="${pageContext.request.contextPath}/booking/my-bookings" class="nav-link">&#8592; My Tickets</a>
        <c:if test="${not empty sessionScope.username}">
            <span class="nav-username">${sessionScope.username}</span>
            <a href="${pageContext.request.contextPath}/user/logout" class="nav-link">Logout</a>
        </c:if>
    </div>
</nav>

<div class="page">
    <div class="ticket">
        <div class="t-header">
            <div class="t-bid">${booking.bookingId}</div>
            <div class="t-movie">${booking.movieTitle}</div>
            <c:choose>
                <c:when test="${booking.confirmed}"><div class="t-stamp-ok">&#10003; CONFIRMED</div></c:when>
                <c:otherwise><div class="t-stamp-cancel">CANCELLED</div></c:otherwise>
            </c:choose>
        </div>

        <div class="t-body">
            <div class="t-row"><span class="t-key">Hall</span><span class="t-val">${booking.hallName}</span></div>
            <div class="t-row"><span class="t-key">Date</span><span class="t-val">${booking.showDate}</span></div>
            <div class="t-row"><span class="t-key">Time</span><span class="t-val">${booking.showTime}</span></div>
            <div class="t-row"><span class="t-key">Show Type</span><span class="t-val">${booking.showTypeLabel}</span></div>
            <div class="t-row"><span class="t-key">Booked by</span><span class="t-val">${booking.username}</span></div>
            <div class="t-row" style="border:none"><span class="t-key">Booked on</span><span class="t-val">${booking.bookingDate}</span></div>

            <div class="seat-table-wrap">
                <div class="st-label">Seats (${booking.seatCount})</div>
                <c:forEach var="sd" items="${seatDetails}">
                    <div class="seat-row-item">
                        <span style="font-weight:600">${sd.seat}
                            <c:choose>
                                <c:when test="${sd.type=='VIP'}"><span class="pill-vip">&#9733; VIP</span></c:when>
                                <c:otherwise><span class="pill-std">Standard</span></c:otherwise>
                            </c:choose>
                        </span>
                        <span style="color:var(--gold)">LKR ${sd.price}</span>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="t-footer">
            <div class="barcode">*${booking.bookingId}*</div>
            <div class="total-block">
                <div class="total-label">Total</div>
                <div class="total-value">LKR ${booking.totalPrice}</div>
            </div>
        </div>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/booking/my-bookings" class="btn-act btn-back">&#8592; All Tickets</a>
        <c:if test="${booking.confirmed}">
            <a href="${pageContext.request.contextPath}/booking/cancel/${booking.bookingId}" class="btn-act btn-cancel-bk" onclick="return confirm('Cancel this booking? Seats will be released.')"><i class="bi bi-x-circle"></i> Cancel Booking</a>
        </c:if>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
