package in.vanna.springfakerapiswagger.repository;

import in.vanna.springfakerapiswagger.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface MovieRepository extends JpaRepository<Movie, Serializable> {
}
