package com.example.pr4.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pr4.R;
import com.example.pr4.Getters.Item;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final static String TAG = "recycler adapter frag";
    private final List<Item> items;

    public RecyclerAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "" + (holder.getAdapterPosition() + 1), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "" + (holder.getAdapterPosition() + 1));
            }
        });
        holder.text.setText(item.getText());
        holder.image.setImageResource(item.getImg());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
        }
    }
}
