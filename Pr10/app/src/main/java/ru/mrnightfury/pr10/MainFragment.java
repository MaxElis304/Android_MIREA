package ru.mrnightfury.pr10;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFragment extends Fragment {
    ExecutorService executor;
    WorkManager workManager;
    ProgressBar executorPB;
    ProgressBar workerPB;
    Button executorButton;

    TextView textView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executor = Executors.newSingleThreadExecutor();
        workManager = WorkManager.getInstance(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        executorPB = view.findViewById(R.id.executor_progressBar);
        workerPB = view.findViewById(R.id.worker_progressBar);
        textView = view.findViewById(R.id.percentageTextView);

        executorButton = view.findViewById(R.id.executor_button);

        executorButton.setOnClickListener(view1 -> {
            ObjectAnimator animator = ObjectAnimator.ofInt(executorPB, "progress", 0, 100);
            animator.setDuration(5000);
            animator.setInterpolator(new DecelerateInterpolator());

            animator.addUpdateListener(animation -> {
                int progress = (int) animation.getAnimatedValue();
                executorPB.setProgress(progress);
                int percentage = (int) (((float) progress / executorPB.getMax()) * 100);
                textView.setText(String.format("%d%%", percentage));
                if (progress == 100) {
                    Toast.makeText(getContext(), "Executor finished", Toast.LENGTH_SHORT).show();
                }
            });

            animator.start();
        });


        view.findViewById(R.id.worker_button).setOnClickListener(view1 -> {
            WorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
            LiveData<WorkInfo> status = workManager.getWorkInfoByIdLiveData(request.getId());
            status.observe(getViewLifecycleOwner(), workInfo -> {
                if (workInfo != null) {
                    if (workInfo.getState().isFinished()) {
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(getContext(), "Worker finished", Toast.LENGTH_SHORT).show();
                            workerPB.setProgress(100);
                        }
                    } else {
                        Data progress = workInfo.getProgress();
                        workerPB.setProgress(progress.getInt("progress", 0));
                    }
                }
            });
            workManager.enqueue(request);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();

    }
}