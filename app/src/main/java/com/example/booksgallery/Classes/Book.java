package com.example.booksgallery.Classes;

public class Book {
    public String authorName;
    public String title;
    public String publisher;
    public String genre;
    public String id;

    public String getAuthorName() {
        return authorName;
    }

    public Book() {
    }

    public Book(String authorName, String title, String publisher, String genre, String id) {
        this.authorName = authorName;
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
