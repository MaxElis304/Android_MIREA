package com.example.pr7.UI_Layer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pr7.ViewModel.MainViewModel;
import com.example.pr7.R;

public class FirstFrag extends Fragment {
    private MainViewModel viewModel;
    private EditText editText;
    private CheckBox checkBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        editText = view.findViewById(R.id.editText1);
        checkBox = view.findViewById(R.id.checkBox);

        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newData) {
                editText.setText(newData);
            }
        });

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newData = editText.getText().toString();
                viewModel.setData(newData);
                if (checkBox.isChecked()) {
                    ((MainActivity) requireActivity()).switchFragments();
                }
                else{
                    Toast.makeText(getActivity(), "Пожалуйста, примите условия соглашения" +
                            ", чтобы продолжить", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
