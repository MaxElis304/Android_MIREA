package com.example.pr4;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pr4.adapters.ArrayAdapter;
import com.example.pr4.Getters.Item;

import java.util.ArrayList;
import java.util.List;


public class ListViewFrag extends Fragment {


    private static final String TAG = "listfragment";

    public ListViewFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_view, container, false);
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            items.add(new Item(R.drawable.ic_launcher_background, "Элемент " + (i + 1)));
        }

        ListView itemsList = view.findViewById(R.id.itemsList);
        android.widget.ArrayAdapter<Item> adapter = new ArrayAdapter(getActivity(), R.layout.list_item, items);
        itemsList.setAdapter(adapter);
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "" + (l + 1), Toast.LENGTH_SHORT).show();
                Log.i(TAG, (l + 1) + "");
            }
        });
        return view;
    }
}