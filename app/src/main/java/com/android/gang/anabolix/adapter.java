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

import com.bumptech.glide.Glide;

public class adapter extends RecyclerView.Adapter<adapter.tiffViewHolder> {
    private static final String TAG = "adapter";

    private OnRecyclerListener mOnRecyclerListener;
    String data1[], data2[];
    int images[];
    Context context;

    public adapter(Context ct, String s1[], String s2[], int img[], OnRecyclerListener onRecyclerListener) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
        this.mOnRecyclerListener = onRecyclerListener;
    }

    @NonNull
    @Override
    public tiffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiff_row, parent, false);
        //tiffViewHolder tiffViewHolder = new tiffViewHolder(view);
        //return tiffViewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tiff_row,parent,false);
        return new tiffViewHolder(view, mOnRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull tiffViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + data1[position]);

        int image_id = images[position];
        //holder.imageView.setImageResource(image_id);
        holder.title.setText(data1[position]);
        holder.description.setText(data2[position]);

                Glide
                .with(context)
                .load(image_id)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class tiffViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ImageView imageView;
        OnRecyclerListener onRecyclerListener;

        public tiffViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            description = itemView.findViewById(R.id.Description);
            imageView = itemView.findViewById(R.id.imageView);
            this.onRecyclerListener = onRecyclerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRecyclerListener.onRecyclerClick(getAdapterPosition());
        }
    }

    public interface OnRecyclerListener {
        void onRecyclerClick(int position);
    }
}
