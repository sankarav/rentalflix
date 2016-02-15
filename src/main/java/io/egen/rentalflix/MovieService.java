package io.egen.rentalflix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Service implementing IFlix interface
 * You can use any Java collection type to store movies
 */
public class MovieService implements IFlix {

    Map<Long, Movie> movieStore = new ConcurrentHashMap<>();

    /**
     * Finds all available movies in the movie store
     *
     * @return list of movies or empty list
     */
    @Override
    public List<Movie> findAll() {
        Collection<Movie> movies = movieStore.values();
        movies.stream().map(movie -> new Movie(movie))
                .collect(Collectors.toList());
        return new ArrayList<>(movieStore.values());
    }

    /**
     * Finds all movies in the movie store which contains <strong>name</strong> in the title
     *
     * @param name String
     * @return list of movies or empty list
     */
    @Override
    public List<Movie> findByName(String name) {
        List<Movie> results = movieStore.values()
                .stream()
                .filter(movie -> movie.isTitleMatch(name))
                .map(movie -> new Movie(movie))
                .collect(Collectors.toList());

        return results;
    }

    /**
     * Create a new movie in the movie store
     *
     * @param movie
     * @return Movie
     */
    @Override
    public Movie create(Movie movie) {
        if(movie == null){
            return null;
        } else {
            movieStore.put(movie.getId(), new Movie(movie));
            return new Movie(movie);
        }
    }

    /**
     * Update an existing movie
     *
     * @param movie
     * @return updated movie or throws <strong>IllegalArgumentException</strong> if movie with this id is not found
     */
    @Override
    public Movie update(Movie movie) {
        Movie out = movieStore.replace(movie.getId(), new Movie(movie));
        if(out == null) {
            throw new IllegalArgumentException("Movie ID : " + movie.getId() + " not found in DB already");
        } {
            return new Movie(out);
        }
    }

    /**
     * Delete an existing movie
     *
     * @param id
     * @return deleted movie or throws <strong>IllegalArgumentException</strong> if movie with this id is not found
     */
    @Override
    public Movie delete(int id) {
        Movie out = movieStore.remove(new Long(id));
        if(out == null) {
            throw new IllegalArgumentException("Movie ID : " + id + " not found in DB already");
        } {
            return out;
        }
    }

    /**
     * Rent the movie with movieId to the <strong>user</strong>.
     * Make sure this movie is not rented already.
     * If it is already rented, throw <strong>IllegalArgumentException</strong>
     *
     * @param movieId
     * @param user
     * @return true: if movie can be rented, false otherwise
     */
    @Override
    public boolean rentMovie(int movieId, String user) {
        Movie movieToRent = movieStore.get(new Long(movieId));
        if(movieToRent != null && user != null){
            boolean rentStatus = movieToRent.rent(user);
            if(!rentStatus) {
                throw new IllegalArgumentException("Movie ID : " + movieId + " is rented already");
            } else {
                return rentStatus;
            }
        } else {
            throw new IllegalArgumentException("Movie ID : " + movieId + "not found in store or null user");
        }
    }

}
