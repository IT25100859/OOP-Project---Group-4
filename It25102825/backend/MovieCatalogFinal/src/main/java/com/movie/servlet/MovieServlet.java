package com.movie.servlet;

import com.movie.model.*;
import com.movie.util.FileHandler;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * MovieServlet – handles all CRUD operations for movies.
 *
 * URL: /movie
 *
 * GET  ?action=list          → gallery.jsp  (all movies)
 * GET  ?action=search        → gallery.jsp  (filtered)
 * GET  ?action=details&id=X  → details.jsp
 * GET  ?action=edit&id=X     → edit-form.jsp
 * GET  ?action=delete&id=X   → deletes and redirects to list
 * GET  ?action=adminForm     → admin-form.jsp
 * POST ?action=add           → adds movie, redirects to list
 * POST ?action=update        → updates movie, redirects to list
 */
@WebServlet("/movie")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)  // 5 MB max image size
public class MovieServlet extends HttpServlet {

    // ── GET ────────────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":      listMovies(request, response);    break;
            case "search":    searchMovies(request, response);  break;
            case "details":   showDetails(request, response);   break;
            case "edit":      showEditForm(request, response);  break;
            case "delete":    deleteMovie(request, response);   break;
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
    // HANDLER METHODS
    // ══════════════════════════════════════════════════════════════════════

    /** READ – list all movies */
    private void listMovies(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            req.setAttribute("movies", FileHandler.getAllMovies());
        } catch (IOException e) {
            req.setAttribute("error", "Could not load movies: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/gallery.jsp").forward(req, res);
    }

    /** READ – search/filter movies */
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

    /** READ – show single movie details */
    private void showDetails(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Movie movie = FileHandler.findById(req.getParameter("id"));
            if (movie == null) {
                res.sendRedirect(req.getContextPath() + "/movie?action=list");
                return;
            }
            req.setAttribute("movie", movie);
        } catch (IOException e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/details.jsp").forward(req, res);
    }

    /** UPDATE – show edit form pre-filled */
    private void showEditForm(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Movie movie = FileHandler.findById(req.getParameter("id"));
            if (movie == null) {
                res.sendRedirect(req.getContextPath() + "/movie?action=list");
                return;
            }
            req.setAttribute("movie", movie);
        } catch (IOException e) {
            req.setAttribute("error", e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/edit-form.jsp").forward(req, res);
    }

    /** CREATE – add new movie */
    private void addMovie(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String id        = FileHandler.generateId();
            String imageName = handleImageUpload(req);
            Movie  movie     = buildMovieFromRequest(req, id, imageName);
            FileHandler.addMovie(movie);
            res.sendRedirect(req.getContextPath() + "/movie?action=list&msg=Movie+added+successfully");
        } catch (Exception e) {
            req.setAttribute("error", "Failed to add movie: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/admin-form.jsp").forward(req, res);
        }
    }

    /** UPDATE – save edited movie */
    private void updateMovie(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            // Retrieve existing movie to preserve old image if no new one uploaded
            Movie  existing  = FileHandler.findById(id);
            String oldImage  = (existing != null && existing.getImagePath() != null)
                               ? existing.getImagePath() : "";

            String newImage   = handleImageUpload(req);
            String finalImage = (!newImage.isEmpty()) ? newImage : oldImage;

            Movie movie = buildMovieFromRequest(req, id, finalImage);
            FileHandler.updateMovie(movie);
            res.sendRedirect(req.getContextPath() + "/movie?action=list&msg=Movie+updated+successfully");
        } catch (Exception e) {
            req.setAttribute("error", "Failed to update: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/edit-form.jsp").forward(req, res);
        }
    }

    /** DELETE – remove a movie */
    private void deleteMovie(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        FileHandler.deleteMovie(req.getParameter("id"));
        res.sendRedirect(req.getContextPath() + "/movie?action=list&msg=Movie+deleted");
    }

    // ── Save uploaded image, return saved filename ────────────────────────
    private String handleImageUpload(HttpServletRequest req) throws Exception {
        Part filePart = req.getPart("image");
        if (filePart == null || filePart.getSize() == 0) return "";

        String originalName = filePart.getSubmittedFileName();
        if (originalName == null || originalName.isEmpty()) return "";

        String ext      = originalName.substring(originalName.lastIndexOf('.'));
        String fileName = UUID.randomUUID().toString() + ext;
        String savePath = FileHandler.IMAGE_DIR + File.separator + fileName;

        try (InputStream  input  = filePart.getInputStream();
             OutputStream output = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[4096];
            int    bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
        return fileName;
    }

    // ── Build Movie object from form parameters ───────────────────────────
    private Movie buildMovieFromRequest(HttpServletRequest req, String id, String imageName) {
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
            case "ACTION": return new ActionMovie(id, title, genre, duration, language, rating, status, extra, imageName);
            case "COMEDY": return new ComedyMovie(id, title, genre, duration, language, rating, status, extra, imageName);
            default:       return new GeneralMovie(id, title, genre, duration, language, rating, status, imageName);
        }
    }
}
