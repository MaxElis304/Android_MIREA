package com.example.pr3;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SecondFrag extends Fragment {
    private static final String TAG = "Frag2";
    private Button button;
    private EditText editText;
    public SecondFrag() {
        super(R.layout.fragment_second);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        editText = view.findViewById(R.id.editTextPhone2);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            editText.setText(bundle.getString("tel"));
        }
        button = view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
        Toast.makeText(getActivity(), TAG + " CREATED", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "CREATED");
        return view;
    }
    public void onButtonClick(View view){
        Bundle result = new Bundle();
        result.putString("Key", editText.getText().toString());
        FragmentManager fragmentManager = getFragmentManager();
        Fragment FirstFrag = new FirstFrag();
        FirstFrag.setArguments(result);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FirstFrag)
                .addToBackStack(null)
                .commit();
        getParentFragmentManager().setFragmentResult(
                "requestKey", result);
        Toast.makeText(getActivity(), "Toaster", Toast.LENGTH_SHORT).show();
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