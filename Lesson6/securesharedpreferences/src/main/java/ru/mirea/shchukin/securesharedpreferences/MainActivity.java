package ru.mirea.shchukin.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.shchukin.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try {
            String mainKey = MasterKeys.getOrCreate(keyGenParameterSpec);
            SharedPreferences securePreferences = EncryptedSharedPreferences.create(
                    "secret_shared_pref",
                    mainKey,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            securePreferences.edit().putString("NAME", "Иван Алексеевич Бунин").apply();
            binding.author.setText(securePreferences.getString("NAME", ""));
            binding.authorPortrait.setImageResource(R.drawable.author);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}