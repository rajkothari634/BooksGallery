package com.example.booksgallery.Classes;

public class Book {
    public String authorName;
    public String title;
    public String publisher;
    public String genre;

    public String getAuthorName() {
        return authorName;
    }

    public Book() {
    }

    public Book(String authorName, String title, String publisher, String genre) {
        this.authorName = authorName;
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
