
package ru.mirea.shchukin.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ru.mirea.shchukin.notebook.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String directoryName = "myDirectory";
    File myDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myDirectory = new File(getFilesDir() + "/" + directoryName);
        if (!myDirectory.exists()) {
            myDirectory.mkdir();
        }

        binding.btnSaveData.setOnClickListener(v -> saveData());
        binding.btnLoadData.setOnClickListener(v -> loadData());
    }

    public void saveData() {
        String fileName = binding.editTextFileName.getText().toString();
        String data = binding.editTextData.getText().toString();

        if (!fileName.isEmpty() && !data.isEmpty()) {
            File file = new File(myDirectory, fileName);
            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter osw = new OutputStreamWriter(fos)) {
                osw.write(data);
                osw.flush();
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Filename or data is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData() {
        String fileName = binding.editTextFileName.getText().toString();

        if (!fileName.isEmpty()) {
            File file = new File(myDirectory, fileName);
            StringBuilder data = new StringBuilder();

            try (FileInputStream fis = new FileInputStream(file);
                 InputStreamReader isr = new InputStreamReader(fis)) {
                int character;
                while ((character = isr.read()) != -1) {
                    data.append((char) character);
                }
                binding.editTextData.setText(data.toString());
                Toast.makeText(this, "Data loaded successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Filename is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
