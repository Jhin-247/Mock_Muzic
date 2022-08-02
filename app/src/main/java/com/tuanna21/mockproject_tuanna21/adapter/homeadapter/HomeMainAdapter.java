package com.tuanna21.mockproject_tuanna21.adapter.homeadapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeMainAdapter extends RecyclerView.Adapter<HomeMainAdapter.HomeMainHolder> {



    @NonNull
    @Override
    public HomeMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMainHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class HomeMainHolder extends RecyclerView.ViewHolder{

        public HomeMainHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
