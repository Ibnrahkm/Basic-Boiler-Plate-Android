package com.ibt.ava.util

/**
 * all required libraries imported here
 */
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets


/**
 * Created by "MD.Ibrahim Khalil" on transactions-Jan-18.
 */
class StatusBarView
/**
 * constructor for getting current context and attribute
 * and setting the layout full screen
 * @param context
 * @param attrs
 */
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    /**
     * Field instances of variables
     */
    private var mStatusBarHeight: Int = 0

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }


    /**
     * if build version sdk greater than or equal to lollipop then the status bar will be consumed
     * @param insets
     * @return
     */

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mStatusBarHeight = insets.systemWindowInsetTop
            return insets.consumeSystemWindowInsets()
        }
        return insets
    }

    /**
     * this method will called on measure the status bar
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight)
    }
}
/**
 * constructor for getting current context
 * @param context
 */