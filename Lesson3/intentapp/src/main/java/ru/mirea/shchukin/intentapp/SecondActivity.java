package ru.mirea.shchukin.intentapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String textDate = (String) getIntent().getSerializableExtra("date");
        TextView textContent = findViewById(R.id.textDate);

        textContent.setText(textDate);
    }
}
