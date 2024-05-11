package ru.mirea.shchukin.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;


public interface BooksApi {
    @GET("/books/1")
    Call<Book> getBookData();
}
