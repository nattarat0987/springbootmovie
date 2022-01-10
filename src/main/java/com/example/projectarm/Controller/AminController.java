package com.example.projectarm.Controller;

import com.example.projectarm.Ropository.GenderRopository;
import com.example.projectarm.Ropository.MovieRepository;
import com.example.projectarm.Service.StoreServiceImp;
import com.example.projectarm.model.Gender;
import com.example.projectarm.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AminController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenderRopository genderRopository;

    @Autowired
    private StoreServiceImp storeServiceImp;

    @GetMapping("")
    public ModelAndView viewHomePage(@PageableDefault(sort = "feature", size = 5) Pageable pageable) {
        Page<Movie> movies = movieRepository.findAll(pageable);
        return new ModelAndView("admin/index").addObject("movies", movies).addObject("genders", new Gender());
    }

    @GetMapping("/movie/new")
    public ModelAndView showNewFilmForm() {
        List<Gender> genders = genderRopository.findAll(Sort.by("feature"));
        return new ModelAndView("admin/newmovie")
                .addObject("movie", new Movie())
                .addObject("genders", genders);
    }

    @PostMapping("/movie/new")
    public ModelAndView registraMovice(@Validated Movie movie, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || movie.getFrontpage().isEmpty()) {
            if (movie.getFrontpage().isEmpty()) {
                bindingResult.rejectValue("frontpage", "MultipartNotEmpty");
            }
            List<Gender> genders = genderRopository.findAll(Sort.by("feature"));
            return new ModelAndView("admin/newmovie")
                    .addObject("movie", movie)
                    .addObject("genders", genders);
        }
        String coveredroute = storeServiceImp.ArchiveStore(movie.getFrontpage());
        movie.setCoveredroute(coveredroute);
        movieRepository.save(movie);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/movie/edit/{id}")
    public ModelAndView editMovies(@PathVariable Integer id) {
        Movie movie = movieRepository.findById(id).get();
        List<Gender> genders = genderRopository.findAll(Sort.by("feature"));
        return new ModelAndView("admin/movie_edit")
                .addObject("movie", movie)
                .addObject("genders", genders);
    }

    @PostMapping("/movie/edit/{id}")
    public ModelAndView saveMoviefdit(@PathVariable Integer id, @Validated Movie movie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Gender> genders = genderRopository.findAll(Sort.by("feature"));
            return new ModelAndView("admin/movie_edit")
                    .addObject("genders", genders)
                    .addObject("movie", movie);
        }
        Optional<Movie> optional = movieRepository.findById(id);
        Movie movie1 = optional.get();
        movie1.setFeature(movie.getFeature());
        movie1.setSynopsis(movie.getSynopsis());
        movie1.setPremieredate(movie.getPremieredate());
        movie1.setYoutubetrailerId(movie.getYoutubetrailerId());
        movie1.setGenders(movie.getGenders());

        if (!movie.getFrontpage().isEmpty()) {
            storeServiceImp.Deletefile(movie1.getCoveredroute());
            String coveredroute = storeServiceImp.ArchiveStore(movie.getFrontpage());
            movie1.setCoveredroute(coveredroute);
        }
        movieRepository.save(movie1);
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping("/movie/delete/{id}")
    public String daieteMoviec(@PathVariable Integer id) {
        Movie movie = movieRepository.findById(id).get();
        movieRepository.delete(movie);
        storeServiceImp.Deletefile(movie.getCoveredroute());
        return "redirect:/admin";
    }

    // @GetMapping("/test")
    // public String tets(){
    // return "test";
    // }
}
