package com.yunshuting.eightdiagrams.mv

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager


class WrapContentHeightViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}