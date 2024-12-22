package com.project.autoservicemobile.common

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

open class OnSwipeTouchListener(ctx: Context) : OnTouchListener {

    companion object {
        private const val MIN_DISTANCE = 100
    }

    private var y1: Float = 0f
    private var y2: Float = 0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->y1 = event.y
            MotionEvent.ACTION_UP->{
                y2 = event.y
                val delta = y2 - y1
                if(abs(delta) > MIN_DISTANCE){
                    if(y2>y1) onDown()
                    else onUp()
                }else{
                    return false
                }
            }
        }
        return true
    }

    open fun onDown(){}
    open fun onUp(){}
}