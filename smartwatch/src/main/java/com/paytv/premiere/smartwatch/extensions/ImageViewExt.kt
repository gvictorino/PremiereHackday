package com.paytv.premiere.smartwatch.extensions

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.paytv.premiere.smartwatch.R

fun ImageView.pulse(context: Context) {
    val pulse: Animation = AnimationUtils.loadAnimation(context, R.anim.pulse)
    this.startAnimation(pulse)
}
