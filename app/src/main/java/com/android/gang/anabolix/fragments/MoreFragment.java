package com.android.gang.anabolix.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gang.anabolix.AnabolixFeature;
import com.android.gang.anabolix.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

public class MoreFragment extends Fragment {
    private RecyclerView mMoreRecycler;
    private ArrayList<AnabolixFeature> mAnabolixFeatures = new ArrayList<>(Arrays.asList(
            new AnabolixFeature("Stats", R.drawable.ic_stats),
            new AnabolixFeature("Pedometer", R.drawable.ic_pedometer),
            new AnabolixFeature("Log out", R.drawable.ic_log_out)
    ));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container, false);
        mMoreRecycler = v.findViewById(R.id.more_recycler_view);
        mMoreRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mMoreRecycler.setAdapter(new MoreAdapter(mAnabolixFeatures));
        return v;
    }

    private class MoreHolder extends RecyclerView.ViewHolder {
        private TextView mFeatureName;
        private ImageView mFeatureImage;

        public MoreHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.more_recycler_item, parent, false));
            mFeatureName = itemView.findViewById(R.id.feature_tv);
            mFeatureImage = itemView.findViewById(R.id.feature_iv);
        }

        public void bind(AnabolixFeature feature) {
            mFeatureName.setText(feature.getName());
            Glide
                    .with(MoreFragment.this)
                    .load(feature.getImage())
                    .override(72, 72)
                    .into(mFeatureImage);
        }
    }

    private class MoreAdapter extends RecyclerView.Adapter<MoreHolder> {
        ArrayList<AnabolixFeature> mFeatures;

        MoreAdapter(ArrayList<AnabolixFeature> features) {
            mFeatures = features;
        }

        @NonNull
        @Override
        public MoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MoreHolder(LayoutInflater.from(requireActivity()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MoreHolder holder, int position) {
            holder.bind(mFeatures.get(position));
        }

        @Override
        public int getItemCount() {
            return mFeatures.size();
        }
    }
}
