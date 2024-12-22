package com.project.autoservicemobile.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.OnSwipeTouchListener

class BottomSheetContainer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    ConstraintLayout(context, attributeSet, defStyle) {

        private val puller: View

        init {
            val root = View.inflate(context, R.layout.bottom_sheet_container_layout, this@BottomSheetContainer)
            puller = root.requireViewById(R.id.puller)

            this.setOnTouchListener(object : OnSwipeTouchListener(context){
                override fun onDown() {
                    hide()
                }

                override fun onUp() {
                    show()
                }
            })

            context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.BottomSheetContainer,
                0,
                0
            ).apply {
                try {
                    val background = getResourceId(R.styleable.BottomSheetContainer_background, 0)

                    if (background != 0) {
                        this@BottomSheetContainer.setBackgroundResource(background)
                    }
                } finally {
                    recycle()
                }
            }
        }

    fun show() {
        puller.animate().translationY(0f).setDuration(450)
        this.animate().translationY(0f).setDuration(450)
    }

    fun hide() {
        puller.animate().translationY(-100f).setDuration(450)
        this.animate().translationY(this.height.toFloat()-90f).setDuration(450)
    }
}