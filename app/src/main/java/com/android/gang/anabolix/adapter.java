package com.android.gang.anabolix;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapter extends RecyclerView.Adapter<adapter.tiffViewHolder> {
    private static final String TAG = "adapter";

    String data1[], data2[];
    int images[];
    Context context;

    public adapter(Context ct, String s1[], String s2[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    @NonNull
    @Override
    public tiffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tiff_row,parent,false);
        return new tiffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tiffViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + data1[position]);
        holder.title.setText(data1[position]);
        holder.description.setText(data2[position]);
        holder.imageView.setImageResource(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class tiffViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;

        public tiffViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            description = itemView.findViewById(R.id.Description);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
