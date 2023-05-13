package com.example.aplikasi;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewItem;

    public ItemViewHolder(View itemView) {
        super(itemView);
        textViewItem = itemView.findViewById(R.id.textViewItem);
    }
}