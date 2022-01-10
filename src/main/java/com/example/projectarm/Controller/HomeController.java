package com.example.projectarm.Controller;
import com.example.projectarm.Ropository.MovieRepository;
import com.example.projectarm.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("")
    public ModelAndView HomeView(){
        List<Movie>  lastmovie = movieRepository.findAll(PageRequest.of(0,5, Sort.by("premieredate").descending())).toList();
        return new ModelAndView("index")
                .addObject("lastmovie",lastmovie);
    }

    @GetMapping("/movie")
    public ModelAndView listMovie(@PageableDefault(sort = "premieredate",size = 8,direction = Sort.Direction.DESC)Pageable pageable){
        Page<Movie> movies = movieRepository.findAll(pageable);
        return new ModelAndView("movies")
                .addObject("movies",movies);
    }

//    @GetMapping("/test")
//    public ModelAndView listtest(@PageableDefault(sort = "premieredate",direction = Sort.Direction.DESC)Pageable pageable){
//        Page<Movie> movies = movieRepository.findAll(pageable);
//        return new ModelAndView("test")
//                .addObject("movies",movies);
//    }

        @GetMapping("/movie/{id}")
        public  ModelAndView movieId(@PathVariable Integer id){
            Movie movie = movieRepository.findById(id).get();
            return new ModelAndView("movie").addObject("movie",movie);
        }















}
