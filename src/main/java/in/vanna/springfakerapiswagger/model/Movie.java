package in.vanna.springfakerapiswagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private String director;
    private int releaseYear;
    private double budget;  // In millions
    private double collection;  // Total collection in millions
    private double internalCollection;  // Domestic collection in millions
    private double externalCollection;  // International collection in millions
    private double imdbRating;

    private String summary;
    private boolean hitOrFlop;  // true if hit, false if flop
    private int releasedInNumberOfTheaters;
}
