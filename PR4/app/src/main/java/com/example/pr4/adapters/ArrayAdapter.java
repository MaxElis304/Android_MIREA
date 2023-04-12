package com.example.pr4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pr4.R;
import com.example.pr4.Getters.Item;

import java.util.List;

public class ArrayAdapter extends android.widget.ArrayAdapter<Item> {
    public ArrayAdapter(@NonNull Context context, int resource, List<Item> items) {
        super(context, resource, items);
        LayoutInflater inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Item item = getItem(position);

        ImageView iconView = convertView.findViewById(R.id.icon);
        iconView.setImageResource(item.getImg());

        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(item.getText());

        return convertView;
    }
}
