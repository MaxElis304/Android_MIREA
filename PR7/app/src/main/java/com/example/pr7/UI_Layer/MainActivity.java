package com.example.pr7.UI_Layer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.pr7.ViewModel.MainViewModel;
import com.example.pr7.R;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private FirstFrag firstFrag;
    private SecondFrag secondFrag;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        firstFrag = new FirstFrag();
        secondFrag = new SecondFrag();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_view, firstFrag)
                .add(R.id.fragment_container_view, secondFrag)
                .hide(secondFrag)
                .commit();
    }

    public void switchFragments() {
        if (firstFrag.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .hide(firstFrag)
                    .show(secondFrag)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .hide(secondFrag)
                    .show(firstFrag)
                    .commit();
        }
    }
}


