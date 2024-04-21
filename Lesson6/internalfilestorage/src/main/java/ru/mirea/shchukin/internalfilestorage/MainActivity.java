package ru.mirea.shchukin.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

import ru.mirea.shchukin.internalfilestorage.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName = "rus_history.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.saveButton.setOnClickListener(v -> SaveData(binding.getRoot()));
    }

    public void SaveData(View view){
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(binding.editText.getText().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}