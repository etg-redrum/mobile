package ru.mirea.shchukin.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchDataFragment extends Fragment {

    private TextView idBookTextView;
    private TextView titleTextView;
    private TextView authorNameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fetch_data, container, false);
        idBookTextView = rootView.findViewById(R.id.idBook);
        titleTextView = rootView.findViewById(R.id.titleBook);
        authorNameTextView = rootView.findViewById(R.id.authorName);
        fetchData();
        return rootView;
    }

    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gutendex.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BooksApi getBooks = retrofit.create(BooksApi.class);

        getBooks.getBookData().enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();
                    if (book != null) {
                        idBookTextView.setText(book.id);
                        titleTextView.setText(book.title);
                        Author author = book.getAuthors().get(0);
                        authorNameTextView.setText(author.name);
                    } else {
                        System.out.println("Книга не найдена");
                    }
                } else {
                    System.out.println("Ошибка при получении данных: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                System.out.println("Ошибка при выполнении запроса: " + t.getMessage());
            }
        });
    }
}