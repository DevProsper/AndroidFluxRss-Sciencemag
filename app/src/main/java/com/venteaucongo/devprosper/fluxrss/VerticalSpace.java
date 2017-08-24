package com.venteaucongo.devprosper.fluxrss;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DevProsper on 24/08/2017.
 */

public class VerticalSpace extends RecyclerView.ItemDecoration {
    int space;

    public VerticalSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView parent, RecyclerView.State state){
        rect.left = space;
        rect.bottom = space;
        rect.right = space;
        if (parent.getChildLayoutPosition(view)==0){
            rect.top = space;
        }
    }
}
