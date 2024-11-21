package cn.moquan.neo4j.demo.movie.controller;

import cn.moquan.neo4j.demo.movie.domain.node.Movie;
import cn.moquan.neo4j.demo.movie.dto.MovieDTOProjection;
import cn.moquan.neo4j.demo.movie.dto.MovieProjection;
import cn.moquan.neo4j.demo.movie.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 11:19 </b><br />
 */
@RestController
@RequestMapping("/demo/movie")
public class MovieController {

    @Resource
    private MovieRepository movieRepository;

    @GetMapping("/movies")
    public List<Movie> findMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/findTwentyMovies")
    public List<Movie> findTwentyMovies() {
        return movieRepository.findMovieSub();
    }

    @GetMapping("/{movieId}")
    public Optional<Movie> findMovieById(@PathVariable String movieId) {
        return movieRepository.findById(movieId);
    }

    @PostMapping("/save")
    public Movie saveMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @DeleteMapping("/delete/{movieId}")
    public void deleteMovie(@PathVariable String movieId) {
        movieRepository.deleteById(movieId);
    }

    @GetMapping("/person")
    List<Movie> findMoviesByPerson(@RequestParam("name") String name) {
        return movieRepository.findMoviesByPerson(name);
    }

    @PutMapping("/incrementImdbVotes")
    Movie incrementImdbVotes(@RequestParam("movieId") String movieId) {
        return movieRepository.incrementImdbVotes(movieId);
    }

    @GetMapping("/movieList")
    List<MovieProjection> findAllMovieProjections() {
        return movieRepository.findAllMovieProjectionsBy();
    }

    @GetMapping("/dtocast")
    List<MovieDTOProjection> findAllMovieDTOProjections() {
        return movieRepository.findAllDTOProjectionsWithCustomQuery();
    }

}
