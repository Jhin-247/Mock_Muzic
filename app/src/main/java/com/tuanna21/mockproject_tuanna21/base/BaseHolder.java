package com.tuanna21.mockproject_tuanna21.base;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder<T extends ViewDataBinding,M> extends RecyclerView.ViewHolder {
    protected T mBinding;

    public BaseHolder(@NonNull T mBinding) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
    }

    protected abstract void bind(M o);
}
