package si.hub.android.dialog

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.viewbinding.ViewBinding
import si.hub.android.R
import si.hub.android.base.BaseActivity
import si.hub.android.databinding.ActivityBaseDialogBinding
import si.hub.android.util.UIUtil

abstract class BaseDialogActivity : BaseActivity() {
    private lateinit var binding: ActivityBaseDialogBinding
    private var animatingOut: Boolean = false
    private var isCreated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dialogContent.addView(contentViewBinding.root)
        onCreateEx(savedInstanceState)
        isCreated = true
    }

    override fun onResume() {
        overridePendingTransition(0, 0)
        if (isCreated) {
            isCreated = false
            animateIn()
        }
        super.onResume()
    }

    override fun finish() {
        if (!animatingOut) {
            animatingOut = true
            animateOut()
        }
    }

    override fun dispatchTouchEvent(touchEvent: MotionEvent): Boolean {
        var dispatched = false
        if (touchEvent.action == MotionEvent.ACTION_DOWN) {
            if (!UIUtil.isTouchInsideView(this, binding.dialogContent, touchEvent)) {
                dispatched = true
                UIUtil.hideKeyboard(this)
                onBackPressed()
            }
        }
        return dispatched || super.dispatchTouchEvent(touchEvent)
    }

    private fun createExitAnimListener(): Animator.AnimatorListener {
        return object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                superFinish()
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
    }

    private fun superFinish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun animateIn() {
        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.contentWindow.startAnimation(zoomIn)
        ObjectAnimator.ofFloat(binding.dimmedBackground, View.ALPHA, 0f, 1f).apply {
            duration = ENTER_ANIMATION_DURATION
            start()
        }
    }

    private fun animateOut() {
        val zoomOut = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        binding.contentWindow.startAnimation(zoomOut)
        ObjectAnimator.ofFloat(binding.dimmedBackground, View.ALPHA, 1f, 0f).apply {
            duration = EXIT_ANIMATION_DURATION
            addListener(createExitAnimListener())
            start()
        }
    }

    protected abstract val contentViewBinding: ViewBinding
    protected abstract fun onCreateEx(savedInstanceState: Bundle?)

    companion object {
        private const val ENTER_ANIMATION_DURATION = 200L
        private const val EXIT_ANIMATION_DURATION = 150L
    }
}