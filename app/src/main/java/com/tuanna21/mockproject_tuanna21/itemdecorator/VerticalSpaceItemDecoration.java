package com.tuanna21.mockproject_tuanna21.itemdecorator;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state
    ) {
        outRect.bottom = verticalSpaceHeight;
        if (parent.getAdapter() != null && parent.getAdapter().getItemCount() - 1 != parent.getChildAdapterPosition(view)) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
