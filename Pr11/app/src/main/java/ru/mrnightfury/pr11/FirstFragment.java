package ru.mrnightfury.pr11;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class FirstFragment extends Fragment {
    private boolean isChecked = false;
    private Animator rotateForwardAnimator;
    private Animator rotateBackwardAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.toSecondFragment_button).setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment);
        });

        ImageView imageView = view.findViewById(R.id.imageView);
        rotateForwardAnimator = AnimatorInflater.loadAnimator(requireContext(), R.animator.rotate_forward);
        rotateBackwardAnimator = AnimatorInflater.loadAnimator(requireContext(), R.animator.rotate_backward);

        imageView.setOnClickListener(v -> {
            if (isChecked) {
                rotateBackwardAnimator.setTarget(imageView);
                rotateBackwardAnimator.start();
            } else {
                rotateForwardAnimator.setTarget(imageView);
                rotateForwardAnimator.start();
            }
            isChecked = !isChecked;
        });
    }
}
