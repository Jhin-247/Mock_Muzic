package com.tuanna21.mockproject_tuanna21.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.tuanna21.mockproject_tuanna21.listener.ToolbarListener;

public abstract class BaseFragment<V extends ViewModel, BD extends ViewDataBinding> extends Fragment {

    protected V mViewModel;
    protected BD mBinding;
    protected ToolbarListener mToolbarListener;
    protected BaseActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
            mToolbarListener = (ToolbarListener) mActivity;
        } else {
            mActivity = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater, container);
        initViewModel();
        initYourView();
        initListener();

        return mBinding.getRoot();
    }

    protected abstract void initListener();

    protected abstract void initViewModel();

    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    }

    protected abstract int getLayoutId();

    protected abstract void initYourView();
}
