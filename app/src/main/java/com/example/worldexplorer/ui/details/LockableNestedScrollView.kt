package com.example.worldexplorer.ui.details

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class LockableNestedScrollView : NestedScrollView {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(ctx, attrs, defStyleAttr)

    var scrollable: Boolean = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return scrollable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return scrollable && super.onInterceptTouchEvent(ev)
    }
}