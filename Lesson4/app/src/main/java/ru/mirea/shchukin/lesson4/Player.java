package ru.mirea.shchukin.lesson4;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.shchukin.lesson4.databinding.ActivityPlayerBinding;

public class Player extends AppCompatActivity {

    private ActivityPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.playButton.setOnClickListener(v -> Log.d("PlayerActivity", "Play button clicked"));
        binding.prevButton.setOnClickListener(v -> Log.d("PlayerActivity", "Previous button clicked"));
        binding.nextButton.setOnClickListener(v -> Log.d("PlayerActivity", "Next button clicked"));
        binding.pauseButton.setOnClickListener(v -> Log.d("PlayerActivity", "More actions clicked"));
    }
}