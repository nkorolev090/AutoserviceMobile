package com.project.autoservicemobile.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getResourceIdOrThrow
import com.project.autoservicemobile.R

class AppBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val leftImage: Int
    private val rightImage: Int
    private val leftBackground: Int
    private val rightBackground: Int
    private val background: Int
    private val titleText: String?

    private val titleTextView: TextView
    private val rightImageView: ImageView
    private val leftImageView: ImageView

    init {
        View.inflate(context, R.layout.app_bar_layout, this@AppBar)
        titleTextView = findViewById<TextView>(R.id.titleTextView)
        rightImageView = findViewById<ImageView>(R.id.rightImage)
        leftImageView = findViewById<ImageView>(R.id.leftImage)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AppBar,
            0,
            0).apply {
            try {
                leftImage = getResourceId(R.styleable.AppBar_leftIcon, 0)
                rightImage = getResourceId(R.styleable.AppBar_rightIcon, 0)
                titleText = getString(R.styleable.AppBar_titleText)
                leftBackground = getResourceId(R.styleable.AppBar_leftBackground, 0)
                rightBackground = getResourceId(R.styleable.AppBar_rightBackground, 0)
                background = getResourceId(R.styleable.AppBar_background, 0)

                titleTextView.text = titleText
                if(rightImage != 0){
                    rightImageView.setImageResource(rightImage)
                }
                if(leftImage != 0){
                    leftImageView.setImageResource(leftImage)
                }
                if(leftBackground != 0){
                    leftImageView.setBackgroundResource(leftBackground)
                }
                if(rightBackground != 0){
                    rightImageView.setBackgroundResource(rightBackground)
                }
                if(background != 0){
                    this@AppBar.setBackgroundResource(background)
                }

            }finally {
                recycle()
            }
        }
    }

    fun setLeftOnClickListener(onClickListener: OnClickListener){
        leftImageView.setOnClickListener(onClickListener)
    }

    fun setRightOnClickListener(onClickListener: OnClickListener){
        rightImageView.setOnClickListener(onClickListener)
    }

    fun setTitleText(text: String){
        titleTextView.text = text
    }

    fun setLeftBackground(resId: Int){
        leftImageView.setBackgroundResource(resId)
    }

    fun setRightBackground(resId: Int){
        rightImageView.setBackgroundResource(resId)
    }
}