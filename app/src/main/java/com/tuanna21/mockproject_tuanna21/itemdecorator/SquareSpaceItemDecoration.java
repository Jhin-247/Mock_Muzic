package com.tuanna21.mockproject_tuanna21.itemdecorator;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SquareSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int mLeft;
    private final int mTop;
    private final int mRight;
    private final int mBottom;


    public SquareSpaceItemDecoration(int mLeft, int mTop, int mRight, int mBottom) {
        this.mLeft = mLeft;
        this.mTop = mTop;
        this.mRight = mRight;
        this.mBottom = mBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state
    ) {
        outRect.bottom = mBottom;
        outRect.top = mTop;
        outRect.right = mRight;
        outRect.left = mLeft;
        if (parent.getAdapter() != null && parent.getAdapter().getItemCount() - 1 != parent.getChildAdapterPosition(view)) {
            outRect.bottom = mBottom;
            outRect.top = mTop;
            outRect.right = mRight;
            outRect.left = mLeft;
        }
    }
}
