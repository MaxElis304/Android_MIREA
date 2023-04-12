package com.example.pr3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstFrag extends Fragment {
    private static final String TAG = "Frag1";
    private Button button;
    private EditText editText;


    public FirstFrag() {
        super(R.layout.fragment_first);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        editText = view.findViewById(R.id.editText1);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            editText.setText(bundle.getString("tel"));
        }
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("tel", editText.getText().toString());
                FragmentManager fragmentManager = getFragmentManager();
                Fragment secondFrag = new SecondFrag();
                secondFrag.setArguments(bundle1);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, secondFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Toast.makeText(getActivity(), TAG + " CREATED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "CREATED");

        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey",
                this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String s = result.getString("Key");
                editText.setText(s);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getActivity(), TAG + " STARTED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "STARTED");
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), TAG + " RESUMED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "RESUMED");
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), TAG + " PAUSED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "PAUSED");
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(), TAG + " STOPPED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "STOPPED");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), TAG + " DESTROYED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "DESTROYED");
    }
}
