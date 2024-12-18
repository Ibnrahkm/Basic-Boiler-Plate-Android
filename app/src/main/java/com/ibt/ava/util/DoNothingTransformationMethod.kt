package com.ibt.ava.util

import android.graphics.Rect
import android.text.method.TransformationMethod
import android.view.View

class DoNothingTransformationMethod : TransformationMethod {
    override fun getTransformation(p0: CharSequence?, p1: View?): CharSequence {
        return ""
    }

    override fun onFocusChanged(p0: View?, p1: CharSequence?, p2: Boolean, p3: Int, p4: Rect?) {
    }
}