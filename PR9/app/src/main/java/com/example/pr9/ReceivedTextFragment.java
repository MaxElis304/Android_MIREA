package com.example.pr9;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReceivedTextFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received_text, container, false);
        String text = getArguments().getString("text");
        if (text != null) {
            TextView tv = view.findViewById(R.id.recceivedTextView);
            tv.setText(text);
        }
        return view;
    }
}