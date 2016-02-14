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

    @After
    public void after() {
        rentalFlix = null;
    }

}
