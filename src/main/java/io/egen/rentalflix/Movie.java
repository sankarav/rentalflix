package io.egen.rentalflix;

/**
 * Entity representing a movie.
 * Fields: id, title, year, language
 */
public class Movie {
	//POJO IMPLEMENTATION GOES HERE

    private long id;
    private String title;
    private int year;
    private String language;
    private boolean rented;
    private String rentedUserName;


    private Movie(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.year = builder.year;
        this.language = builder.language;
    }

    /**
     * Deep Copy constructor. Note: String is immutable, other attributes are primitives
     * @param src
     */
    public Movie(Movie src){
        this.id = src.getId();
        this.title = src.getTitle();
        this.year = src.getYear();
        this.language = src.getLanguage();
        setRented(src.isRented());
        setRentedUserName(src.getRentedUserName());
    }

    public static class Builder{
        private final long id;
        private final String title;

        private int year;
        private String language;

        public Builder(long id, String title){
            this.id = id;
            this.title = title;
        }

        public Builder year(int year){
            this.year = year;
            return this;
        }

        public Builder language(String language){
            this.language = language;
            return this;
        }

        public Movie build(){
            return new Movie(this);
        }
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Rents and returns true if movie is not rented already; if not false. Does it atomic.
     * @param userName userName to rent the movie to
     * @return
     */
    public synchronized boolean rent(final String userName){
        if(isRented()){
            return false;
        } else {
            setRentedUserName(userName);
            setRented(true);
            return true;
        }
    }

    public boolean isTitleMatch(String titleToSearch){
        return title.equals(titleToSearch);
    }

    private void setRentedUserName(String rentedUserName) {
        this.rentedUserName = rentedUserName;
    }

    public String getRentedUserName() {
        return rentedUserName;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    private boolean isRented(){
        return rented;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return getId() == movie.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", language='" + (language != null ? language : null) + '\'' +
                '}';
    }
}
