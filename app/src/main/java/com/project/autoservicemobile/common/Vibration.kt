package com.project.autoservicemobile.common

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.project.autoservicemobile.common.models.VibrateTypeEnum

fun vibrate(context: Context, vibrateTypeEnum: VibrateTypeEnum){
    val vibe: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val effect = when(vibrateTypeEnum){
        VibrateTypeEnum.SHORT -> VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        VibrateTypeEnum.MEDIUM_WAVE -> VibrationEffect.createWaveform( longArrayOf(0, 3, 2, 3, 2, 3, 2, 3),
            intArrayOf(0, 80, 0, 100, 0, 60, 0, 100), -1)
        VibrateTypeEnum.LONG -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            VibrationEffect.createOneShot(3000, VibrationEffect.EFFECT_HEAVY_CLICK)
        } else {
            VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE)
        }
    }

    vibe.vibrate(effect)
}