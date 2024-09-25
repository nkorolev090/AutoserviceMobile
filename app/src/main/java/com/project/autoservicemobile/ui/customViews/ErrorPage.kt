package com.project.autoservicemobile.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
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
        init {
            requireViewById<TextView>(R.id.text_title).setText(R.string.no_connection_text)
        }
    }
}
