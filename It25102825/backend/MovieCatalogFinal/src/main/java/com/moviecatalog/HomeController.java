package com.moviecatalog;

import com.movie.model.Movie;
import com.movie.util.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Movie> allMovies  = FileHandler.getAllMovies();
            List<Movie> nowShowing = new ArrayList<>();

            for (Movie m : allMovies) {
                if ("Now Showing".equals(m.getStatus())) {
                    nowShowing.add(m);
                }
            }

            model.addAttribute("allMovies",  allMovies);
            model.addAttribute("nowShowing", nowShowing);

        } catch (Exception e) {
            model.addAttribute("allMovies",  new ArrayList<>());
            model.addAttribute("nowShowing", new ArrayList<>());
        }

        return "index"; // resolves to /WEB-INF/jsp/index.jsp
    }
}
