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

import com.tuanna21.mockproject_tuanna21.listener.FragmentChangeListener;
import com.tuanna21.mockproject_tuanna21.listener.ToolbarListener;
import com.tuanna21.mockproject_tuanna21.utils.ScreenUtils;

public abstract class BaseFragment<VM extends ViewModel, VB extends ViewDataBinding> extends Fragment {

    protected VM mViewModel;
    protected VB mBinding;
    protected ToolbarListener mToolbarListener;
    protected FragmentChangeListener mFragmentChangeListener;
    protected BaseActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
            mToolbarListener = (ToolbarListener) mActivity;
            mFragmentChangeListener = (FragmentChangeListener) mActivity;
        } else {
            mActivity = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater, container);
        initViewModel();
        initToolbar();
        initData();
        initYourView();
        initListener();
        initObserver();


        return mBinding.getRoot();
    }

    protected abstract void initData();

    protected abstract void initToolbar();

    protected abstract void initObserver();

    protected abstract void initListener();

    protected abstract void initViewModel();

    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    }

    protected abstract int getLayoutId();

    protected abstract void initYourView();

    protected int getScreenWidth() {
        return new ScreenUtils().getScreenWidth(mActivity);
    }

    protected int getScreenHeight() {
        return new ScreenUtils().getScreenHeight(mActivity);
    }
}
