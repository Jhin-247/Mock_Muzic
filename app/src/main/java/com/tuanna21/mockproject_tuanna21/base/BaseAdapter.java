package com.tuanna21.mockproject_tuanna21.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<VB extends ViewDataBinding, M> extends RecyclerView.Adapter<BaseHolder<VB, M>> {

    protected Activity mActivity;
    private List<M> mData;

    public BaseAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        mData = new ArrayList<>();
    }

    protected List<M> getData() {
        return mData;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<M> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    protected M getItemAt(int position) {
        return mData.get(position);
    }

    @NonNull
    @Override
    public BaseHolder<VB, M> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewDataBinding(parent, viewType);
    }

    protected abstract BaseHolder<VB, M> getViewDataBinding(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(@NonNull BaseHolder<VB, M> holder, int position) {
        bindView(holder.getBinding(), position);
    }

    protected abstract void bindView(VB binding, int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
