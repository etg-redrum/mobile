package ru.mirea.shchukin.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundWorker extends Worker {
    private static final String TAG = "BackgroundWorker";

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(3000);
            Log.d(TAG, "Work is done successfully.");
        } catch (InterruptedException e) {
            Log.e(TAG, "Error during work execution", e);
            return Result.failure();
        }
        return Result.success();
    }
}
