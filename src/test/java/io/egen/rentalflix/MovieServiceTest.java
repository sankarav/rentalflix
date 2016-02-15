package io.egen.rentalflix;

//import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;


/**
 * JUnit test cases for MovieService
 */
public class MovieServiceTest {

    private static Movie [] moviesList = {
            new Movie.Builder(100, "Black").year(2007)
                    .language("English")
                    .build(),
            new Movie.Builder(101, "Black")
                    .year(2008)
                    .language("English")
                    .build(),
            new Movie.Builder(102, "Black Hawk")
                    .year(2000)
                    .language("English")
                    .build()
    };

    private IFlix rentalFlix;

    @Before
    public void before() {
        rentalFlix = new MovieService();
    }

    @Test
    public void testfindAll_AllMoviesInStore(){
        for (Movie movie : moviesList) {
            rentalFlix.create(movie);
        }
        List<Movie> actual = rentalFlix.findAll();
        assertArrayEquals(moviesList, actual.toArray());
    }

    @Test
    public void testfindAll_ZeroMoviesInStore(){
        assertEquals(0, rentalFlix.findAll().size());
    }

    @Test
    public void testfindByName_ZeroAppropriateMoviesInStore(){
        for (Movie movie : moviesList) {
            rentalFlix.create(movie);
        }

        List<Movie> actual = rentalFlix.findByName("White");
        assertEquals(0, actual.size());
    }

    @Test
    public void testCreate_WithNullMovie(){
        Movie actual = rentalFlix.create(null);
        assertNull("Return null for null movie creation", actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdate_NonExistingMovie(){
        rentalFlix.create(moviesList[0]);
        rentalFlix.update(moviesList[1]);
    }

    @Test
    public void testUpdate_ExistingMovie(){
        rentalFlix.create(moviesList[0]);
        Movie toUpdate = new Movie.Builder(100, "BlackOwl").year(2007)
                .language("English")
                .build();
        Movie actual = rentalFlix.update(toUpdate);
        assertEquals(moviesList[0].getTitle(), actual.getTitle());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDelete_NonExistingMovie(){
        rentalFlix.delete((int) moviesList[0].getId());
    }

    @Test
    public void testDelete_ExistingMovie(){
        rentalFlix.create(moviesList[0]);
        Movie actual = rentalFlix.delete((int) moviesList[0].getId());
        assertEquals(moviesList[0].getId(), actual.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRentMovie_RentAlreadyRentedMovie(){
        Movie rentedMovie = new Movie(moviesList[0]);
        rentedMovie.rent("Bob");

        rentalFlix.create(rentedMovie);
        rentalFlix.rentMovie((int) rentedMovie.getId(), "NewUser");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRentMovie_RentForNullUser(){
        Movie rentedMovie = new Movie(moviesList[0]);
        rentedMovie.rent("Bob");

        rentalFlix.create(rentedMovie);
        rentalFlix.rentMovie((int) rentedMovie.getId(), null);
    }

    @Test
    public void testRentMovie_RentNotAlreadyRentedMovie(){
        rentalFlix.create(moviesList[0]);
        boolean actual = rentalFlix.rentMovie((int) moviesList[0].getId(), "Bob");
        assertTrue(actual);
    }

    @After
    public void after() {
        rentalFlix = null;
    }

}
