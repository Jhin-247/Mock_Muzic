package com.tuanna21.mockproject_tuanna21.adapter.navigation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.databinding.ItemNavigationBinding;
import com.tuanna21.mockproject_tuanna21.db.model.NavigationItem;

import java.util.List;

public class FakeItemAdapterAdapter extends RecyclerView.Adapter<FakeItemAdapterAdapter.NavigationItemHolder> {
    private final List<NavigationItem> mNavigationItemList;

    public FakeItemAdapterAdapter(List<NavigationItem> mNavigationItemList) {
        this.mNavigationItemList = mNavigationItemList;
    }

    @NonNull
    @Override
    public NavigationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NavigationItemHolder(
                ItemNavigationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationItemHolder holder, int position) {
        NavigationItem navigationItem = mNavigationItemList.get(position);
        holder.mBinding.tvIcon.setText(navigationItem.getName());
        Glide.with(holder.mBinding.ivIcon).load(navigationItem.getImage()).into(holder.mBinding.ivIcon);
    }

    @Override
    public int getItemCount() {
        return mNavigationItemList == null ? 0 : mNavigationItemList.size();
    }

    public static class NavigationItemHolder extends RecyclerView.ViewHolder {
        ItemNavigationBinding mBinding;

        public NavigationItemHolder(@NonNull ItemNavigationBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
