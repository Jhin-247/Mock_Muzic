package com.tuanna21.mockproject_tuanna21.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<VB extends ViewDataBinding, M> extends RecyclerView.Adapter<BaseHolder<VB, M>> {

    private List<M> mData;
    private Activity mActivity;

    public BaseAdapter(Activity mActivity) {
        this.mActivity = mActivity;
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
        M object = getItemAt(position);
        holder.bind(object);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
