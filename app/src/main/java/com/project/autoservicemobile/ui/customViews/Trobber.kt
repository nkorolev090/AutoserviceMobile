package com.project.autoservicemobile.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.autoservicemobile.R

class Trobber (private val context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val trobberImage: ImageView

    init {
        View.inflate(context, R.layout.trobber_layout, this@Trobber)

        trobberImage = requireViewById<ImageView>(R.id.trobber_image)

        startAnim()
    }

    private fun startAnim(){
        var anim = RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = 1400
        anim.repeatCount = Animation.INFINITE

        trobberImage.startAnimation(anim)
    }

    private fun clearAnim() {
        trobberImage.clearAnimation()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        when(visibility){
            VISIBLE -> {
                clearAnim()
                startAnim()
            }
            GONE -> {
                clearAnim()
            }
            INVISIBLE -> {
                clearAnim()
            }
        }
    }
}