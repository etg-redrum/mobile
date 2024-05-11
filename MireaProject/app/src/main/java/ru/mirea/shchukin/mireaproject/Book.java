package ru.mirea.shchukin.mireaproject;

import java.util.List;

public class Book {
    String id;
    String title;
    private List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }
}
