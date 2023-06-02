package ru.mrnightfury.pr10;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    private final static String TAG = Worker.class.getSimpleName();

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        setProgressAsync(new Data.Builder().putInt("progress", 0).build());
    }

    @NonNull
    @Override
    public Result doWork() {
        for(int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(500);
                setProgressAsync(new Data.Builder().putInt("progress", i * 10).build());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.success();
    }
}
