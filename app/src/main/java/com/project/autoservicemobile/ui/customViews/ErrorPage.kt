package com.project.autoservicemobile.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.autoservicemobile.R

sealed class ErrorPage
    constructor(
    context: Context,
    resource: Int) : ConstraintLayout(context)
{
    init {
        View.inflate(context, resource, this@ErrorPage)
    }

    fun showWithAnim(){
        val anim = AnimationUtils.loadAnimation(context, R.anim.show_alpha)
        startAnimation(anim)
    }

    fun hideWithAnim(){
        val anim = AnimationUtils.loadAnimation(context, R.anim.hide_alpha)
        startAnimation(anim)
    }

    class NoConnection(
        context: Context,
    ) : ErrorPage(context, R.layout.no_connection_layout){
        init {
            requireViewById<TextView>(R.id.text_title).setText(R.string.no_connection_text)
        }
    }

    class NoContent(
        context: Context,
    ) : ErrorPage(context, R.layout.no_content_layout){

        val errorTextView = requireViewById<TextView>(R.id.text_description)
        val errorTitleTextView = requireViewById<TextView>(R.id.text_title)
        init {
            errorTextView.setText(R.string.no_content_text)
            errorTitleTextView.setText(R.string.error_text)
        }
    }

    class NoSlots(
        context: Context
    ) : ErrorPage(context, R.layout.no_slots_layout){
        private val setDateButton = requireViewById<Button>(R.id.setDateBtn)
        init {
            requireViewById<TextView>(R.id.text_description).setText(R.string.no_slots_text)
            with(requireViewById<TextView>(R.id.text_title)){
                setText(R.string.error_text)
                isAllCaps = true
            }
            setDateButton.setText(R.string.slots_set_date_text)
        }

        fun setOnButtonClick(onClickListener: OnClickListener)
            = setDateButton.setOnClickListener(onClickListener)
    }
}
