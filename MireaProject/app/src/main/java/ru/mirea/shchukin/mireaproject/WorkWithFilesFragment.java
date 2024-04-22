package ru.mirea.shchukin.mireaproject;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.shchukin.mireaproject.databinding.FragmentWorkWithFilesBinding;

public class WorkWithFilesFragment extends Fragment {
    private FragmentWorkWithFilesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWorkWithFilesBinding.inflate(inflater, container, false);
        binding.fabAddFile.setOnClickListener(v -> showAddFileDialog());
        return binding.getRoot();
    }

    private void showAddFileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setItems(new CharSequence[]{"Choce file to convert WORD"}, (dialog, which) -> {
            if (which == 0) {
                convertTxtToDocx();
            }
        });
        builder.create().show();
    }

    private void convertTxtToDocx() {
        List<String> lines = new ArrayList<>();
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.text);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            createDocx(lines);
        } catch (Exception e) {
            Log.w("FileConversion", "Failed to read from file: " + e.getMessage());
        }
    }

    private void createDocx(List<String> lines) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!path.exists() && !path.mkdirs()) {
            Log.w("FileCreation", "Failed to create directory: " + path);
            return;
        }
        File file = new File(path, "converted_document.docx");
        try (XWPFDocument doc = new XWPFDocument()) {
            FileOutputStream out = new FileOutputStream(file);
            for (String line : lines) {
                XWPFParagraph p = doc.createParagraph();
                p.createRun().setText(line);
            }
            doc.write(out);
            out.close();
            Log.i("FileCreation", "Document created successfully at " + file.getAbsolutePath());
        } catch (Exception e) {
            Log.w("FileCreation", "Error writing document: " + e.getMessage());
        }
    }
}
