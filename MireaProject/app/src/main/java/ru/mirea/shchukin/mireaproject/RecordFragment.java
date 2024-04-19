package ru.mirea.shchukin.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment {
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private Button recordButton;
    private Button playButton;
    private String fileName;
    private boolean isStartRecording = true;
    private boolean isStartPlaying = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        recordButton = view.findViewById(R.id.recordButton);
        playButton = view.findViewById(R.id.playButton);
        playButton.setEnabled(false);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }

        fileName = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audiorecordtest.3gp").getAbsolutePath();

        recordButton.setOnClickListener(v -> {
            if (isStartRecording) {
                startRecording();
                recordButton.setText("Stop recording");
                playButton.setEnabled(false);
            } else {
                stopRecording();
                recordButton.setText("Start recording");
                playButton.setEnabled(true);
            }
            isStartRecording = !isStartRecording;
        });

        playButton.setOnClickListener(v -> {
            if (isStartPlaying) {
                startPlaying();
                playButton.setText("Stop playing");
                recordButton.setEnabled(false);
            } else {
                stopPlaying();
                playButton.setText("Start playing");
                recordButton.setEnabled(true);
            }
            isStartPlaying = !isStartPlaying;
        });

        return view;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            // Log error
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            // Log error
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permissions granted
        }
    }
}
