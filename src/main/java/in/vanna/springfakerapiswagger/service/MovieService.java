package in.vanna.springfakerapiswagger.service;

import com.github.javafaker.Faker;
import in.vanna.springfakerapiswagger.model.Movie;
import in.vanna.springfakerapiswagger.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private final Faker faker = new Faker();

    @CachePut(value = "movies", key = "#result.id")
    public Movie createMovie() {
        delay();
        Movie movie = new Movie();
        movie.setTitle(faker.book().title());
        movie.setGenre(faker.book().genre());
        movie.setDirector(faker.name().fullName());
        movie.setReleaseYear(faker.number().numberBetween(1900, 2024));
        movie.setBudget(faker.number().randomDouble(2, 50, 300));
        movie.setCollection(faker.number().randomDouble(2, 50, 1000));
        movie.setInternalCollection(faker.number().randomDouble(2, 20, 500));
        movie.setExternalCollection(faker.number().randomDouble(2, 20, 500));
        movie.setImdbRating(faker.number().randomDouble(1, 1, 10));
        movie.setSummary(faker.lorem().paragraph());


        movie.setHitOrFlop(faker.bool().bool());
        movie.setReleasedInNumberOfTheaters(faker.number().numberBetween(50, 5000));

        return movieRepository.save(movie);
    }

    @Cacheable(value = "movies")
    public List<Movie> getAllMovies() {
        delay();
        return movieRepository.findAll();
    }

//    @Cacheable(value = "movies", key = "#id")
    public Optional<Movie> getMovieById(Long id) {
        delay();
        return movieRepository.findById(id);
    }

    @CachePut(value = "movies", key = "#id")
    public Movie updateMovie(Long id, Movie movieDetails) {
        delay();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setDirector(movieDetails.getDirector());
        movie.setReleaseYear(movieDetails.getReleaseYear());
        movie.setBudget(movieDetails.getBudget());
        movie.setCollection(movieDetails.getCollection());
        movie.setInternalCollection(movieDetails.getInternalCollection());
        movie.setExternalCollection(movieDetails.getExternalCollection());
        movie.setImdbRating(movieDetails.getImdbRating());
        movie.setSummary(movieDetails.getSummary());
        movie.setHitOrFlop(movieDetails.isHitOrFlop());
        movie.setReleasedInNumberOfTheaters(movieDetails.getReleasedInNumberOfTheaters());
        return movieRepository.save(movie);
    }

    @CacheEvict(value = "movies", key = "#id")
    public void deleteMovie(Long id) {
        delay();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieRepository.delete(movie);
    }

    private void delay() {
        try {
            Thread.sleep(2000); // 2-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}