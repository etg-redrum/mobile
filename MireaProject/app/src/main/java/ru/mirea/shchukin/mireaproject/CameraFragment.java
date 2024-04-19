package ru.mirea.shchukin.mireaproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private boolean isWork = false;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private ImageView imageView1, imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        checkPermissions();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCamera();
    }

    private void checkPermissions() {
        int cameraPermissionStatus = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    private void setupCamera() {
        ActivityResultCallback<ActivityResult> callback = result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (imageView1.getDrawable() == null) {
                    imageView1.setImageURI(imageUris.get(0));
                } else {
                    imageView2.setImageURI(imageUris.get(1));
                }
            }
        };

        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), callback);

        View captureButton = getView().findViewById(R.id.captureButton);
        captureButton.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (isWork && imageUris.size() < 2) {
                try {
                    File photoFile = createImageFile();
                    String authorities = getActivity().getApplicationContext().getPackageName() + ".fileprovider";
                    Uri imageUri = FileProvider.getUriForFile(getActivity(), authorities, photoFile);
                    imageUris.add(imageUri);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    cameraActivityResultLauncher.launch(cameraIntent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        View generateCollageButton = getView().findViewById(R.id.generateCollageButton);
        generateCollageButton.setOnClickListener(v -> generateCollage());
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    private void generateCollage() {
        if (imageUris.size() < 2) {
            Toast.makeText(getActivity(), "Коллаж можно создать только с двумя изображениями.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Bitmap firstImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUris.get(0));
            Bitmap secondImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUris.get(1));

            int width = firstImage.getWidth() + secondImage.getWidth();
            int height = Math.max(firstImage.getHeight(), secondImage.getHeight());

            Bitmap collageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(collageBitmap);

            canvas.drawBitmap(firstImage, 0, 0, null);
            canvas.drawBitmap(secondImage, firstImage.getWidth(), 0, null);

            imageView1.setImageBitmap(collageBitmap);

            firstImage.recycle();
            secondImage.recycle();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Ошибка при создании коллажа: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
}
