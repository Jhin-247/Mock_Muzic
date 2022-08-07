package com.tuanna21.mockproject_tuanna21.screen.main.fakeadapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tuanna21.mockproject_tuanna21.base.BaseAdapter;
import com.tuanna21.mockproject_tuanna21.base.BaseHolder;
import com.tuanna21.mockproject_tuanna21.data.model.NavigationItem;
import com.tuanna21.mockproject_tuanna21.databinding.ItemNavigationBinding;

public class FakeItemAdapterAdapter extends BaseAdapter<ItemNavigationBinding, NavigationItem> {

    public FakeItemAdapterAdapter(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected BaseHolder<ItemNavigationBinding, NavigationItem> getViewDataBinding(ViewGroup parent, int viewType) {
        return new BaseHolder<>(
                ItemNavigationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    protected void bindView(ItemNavigationBinding binding, int position) {
        NavigationItem navigationItem = getItemAt(position);
        binding.tvIcon.setText(navigationItem.getName());
        Glide.with(binding.ivIcon).load(navigationItem.getImage()).into(binding.ivIcon);
    }
}
