package si.hub.android.util

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

object UIUtil {
    /**
     * Check for touch event if is inside of view
     *
     * @param activity   Activity
     * @param view       View to check fo touch events
     * @param touchEvent touch event
     * @return flag to determine if touch is inside requested view
     */
    fun isTouchInsideView(activity: Activity, view: View, touchEvent: MotionEvent): Boolean {
        return isTouchInsideView(activity, view, touchEvent.rawX.toInt(), touchEvent.rawY.toInt())
    }

    /**
     * Check for touch event if is inside of view
     *
     * @param activity Activity to get display size
     * @param view     View to check fo touch events
     * @param x        X position of touch event
     * @param y        Y position of touch event
     * @return flag to determine if touch is inside requested view
     */
    private fun isTouchInsideView(activity: Activity, view: View, x: Int, y: Int): Boolean {
        val rect = Rect()
        val local = view.getGlobalVisibleRect(rect)

        if (!local) {
            // view's global coordinates were not correct, so we need to calculate proper ones
            val arr = IntArray(2)
            view.getLocationOnScreen(arr)
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val left = (width - (rect.right - rect.left)) / 2
            val top = arr[1]
            val right = width - left
            val bottom = top + view.height
            rect.set(left, top, right, bottom)
        }
        return rect.contains(x, y)
    }

    /**
     * Helper to hide keyboard on an activity.
     * Where possible, use [.hideKeyboard] instead.
     *
     * @param activity the current activity
     */
    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.findViewById<View>(android.R.id.content).windowToken,
            0
        )
    }
}