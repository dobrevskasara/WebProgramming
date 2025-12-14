package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;

@Entity
//@Data
//@Getter @Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    // private String genre;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    private double averageRating;
    @ManyToOne
    private Author author;

    public Book(String title, Genre genre, double averageRating, Author author) {
        this.title = title;
        this.genre = genre;
        this.averageRating = averageRating;
        this.author = author;
    }

    public Book() {

    }

    public Book(String title, Genre genre, double averageRating) {
        this.title = title;
        this.genre = genre;
        this.averageRating = averageRating;
    }

    // //   @Transient
//    private int likes = 0;
//
//    public int getLikes() { return likes; }
//    public void setLikes(int likes) { this.likes = likes; }

    public String getAuthorName() {
        return author != null  ? author.getName() + " " + author.getSurname() : "None";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
