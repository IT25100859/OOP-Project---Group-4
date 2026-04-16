package com.movie.servlet;

import com.movie.model.*;
import com.movie.util.FileHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * MovieServlet – central servlet handling all CRUD operations for movies.
 *
 * URL mapping (configured in web.xml):  /movie
 *
 * GET actions:
 *   /movie?action=list              → gallery.jsp  (all movies)
 *   /movie?action=search&genre=X    → gallery.jsp  (filtered)
 *   /movie?action=search&language=X → gallery.jsp  (filtered)
 *   /movie?action=details&id=X      → details.jsp
 *   /movie?action=edit&id=X         → edit-form.jsp
 *   /movie?action=delete&id=X       → redirect to list
 *   /movie?action=adminForm         → admin-form.jsp
 *
 * POST actions:
 *   /movie?action=add               → adds movie, redirect to list
 *   /movie?action=update            → updates movie, redirect to list
 */
@WebServlet("/movie")
public class MovieServlet extends HttpServlet {

    // ── GET ────────────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":      listMovies(request, response);      break;
            case "search":    searchMovies(request, response);    break;
            case "details":   showDetails(request, response);     break;
            case "edit":      showEditForm(request, response);    break;
            case "delete":    deleteMovie(request, response);     break;
            case "adminForm":
                request.getRequestDispatcher("/WEB-INF/jsp/admin-form.jsp")
                       .forward(request, response);
                break;
            default:          listMovies(request, response);
        }
    }

    // ── POST ───────────────────────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":    addMovie(request, response);    break;
            case "update": updateMovie(request, response); break;
            default:
                response.sendRedirect(request.getContextPath() + "/movie?action=list");
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // PRIVATE HANDLER METHODS
    // ══════════════════════════════════════════════════════════════════════

    /** READ – List all movies */
    private void listMovies(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            List<Movie> movies = FileHandler.getAllMovies();
            req.setAttribute("movies", movies);
            req.setAttribute("pageTitle", "Movie Gallery");
        } catch (IOException e) {
            req.setAttribute("error", "Could not load movies: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/gallery.jsp").forward(req, res);
    }

    /** READ – Search / filter movies */
    private void searchMovies(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String genre    = req.getParameter("genre");
        String language = req.getParameter("language");
        String title    = req.getParameter("title");
        List<Movie> movies;

        try {
            if (genre != null && !genre.isEmpty()) {
                movies = FileHandler.searchByGenre(genre);
                req.setAttribute("searchLabel", "Genre: " + genre);
            } else if (language != null && !language.isEmpty()) {
                movies = FileHandler.searchByLanguage(language);
                req.setAttribute("searchLabel", "Language: " + language);
            } else if (title != null && !title.isEmpty()) {
                movies = FileHandler.searchByTitle(title);
                req.setAttribute("searchLabel", "Title: " + title);
            } else {
                movies = FileHandler.getAllMovies();
            }
            req.setAttribute("movies", movies);
        } catch (IOException e) {
            req.setAttribute("error", "Search failed: " + e.getMessage());
            req.setAttribute("movies", java.util.Collections.emptyList());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/gallery.jsp").forward(req, res);
    }

    /** READ – Show single movie details */
    private void showDetails(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            Movie movie = FileHandler.findById(id);
            if (movie == null) {
                res.sendRedirect(req.getContextPath() + "/movie?action=list&error=notfound");
                return;
            }
            req.setAttribute("movie", movie);
        } catch (IOException e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/details.jsp").forward(req, res);
    }

    /** UPDATE – Show edit form pre-filled */
    private void showEditForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            Movie movie = FileHandler.findById(id);
            if (movie == null) {
                res.sendRedirect(req.getContextPath() + "/movie?action=list&error=notfound");
                return;
            }
            req.setAttribute("movie", movie);
        } catch (IOException e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/edit-form.jsp").forward(req, res);
    }

    /** CREATE – Add a new movie */
    private void addMovie(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Movie movie = buildMovieFromRequest(req, FileHandler.generateId());
            FileHandler.addMovie(movie);
            res.sendRedirect(req.getContextPath() + "/movie?action=list&msg=Movie+added+successfully");
        } catch (Exception e) {
            req.setAttribute("error", "Failed to add movie: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/admin-form.jsp").forward(req, res);
        }
    }

    /** UPDATE – Save edited movie */
    private void updateMovie(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            Movie movie = buildMovieFromRequest(req, id);
            FileHandler.updateMovie(movie);
            res.sendRedirect(req.getContextPath() + "/movie?action=list&msg=Movie+updated+successfully");
        } catch (Exception e) {
            req.setAttribute("error", "Failed to update movie: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/edit-form.jsp").forward(req, res);
        }
    }

    /** DELETE – Remove a movie */
    private void deleteMovie(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String id = req.getParameter("id");
        try {
            FileHandler.deleteMovie(id);
            res.sendRedirect(req.getContextPath() + "/movie?action=list&msg=Movie+deleted");
        } catch (IOException e) {
            res.sendRedirect(req.getContextPath() + "/movie?action=list&error=Delete+failed");
        }
    }

    // ── Helper: Build a Movie object from form parameters ─────────────────
    private Movie buildMovieFromRequest(HttpServletRequest req, String id) {
        String title    = req.getParameter("title").trim();
        String genre    = req.getParameter("genre").trim();
        int    duration = Integer.parseInt(req.getParameter("duration").trim());
        String language = req.getParameter("language").trim();
        String rating   = req.getParameter("rating").trim();
        String status   = req.getParameter("status").trim();
        String type     = req.getParameter("type").trim();
        String extra    = req.getParameter("extra") != null
                          ? req.getParameter("extra").trim() : "N/A";

        switch (type) {
            case "ACTION": return new ActionMovie(id, title, genre, duration,
                                                  language, rating, status, extra);
            case "COMEDY": return new ComedyMovie(id, title, genre, duration,
                                                  language, rating, status, extra);
            default:       return new GeneralMovie(id, title, genre, duration,
                                                   language, rating, status);
        }
    }
}
