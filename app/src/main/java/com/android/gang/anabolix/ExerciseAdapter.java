package com.android.gang.anabolix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import timber.log.Timber;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.tiffViewHolder> {
    private OnRecyclerListener mOnRecyclerListener;
    private String[] data1;
    private String[] data2;
    private int[] images;
    private Context context;

    public ExerciseAdapter(Context ct, String[] s1, String[] s2, int[] img, OnRecyclerListener onRecyclerListener) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
        this.mOnRecyclerListener = onRecyclerListener;
    }

    @NonNull
    @Override
    public tiffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tiff_row, parent, false);
        return new tiffViewHolder(view, mOnRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull tiffViewHolder holder, int position) {
        Timber.d("onBindViewHolder: %s", data1[position]);

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

        tiffViewHolder(@NonNull View itemView, OnRecyclerListener onRecyclerListener) {
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
