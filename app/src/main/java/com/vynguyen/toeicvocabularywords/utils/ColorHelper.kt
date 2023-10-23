package com.vynguyen.toeicvocabularywords.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.view.View

object ColorHelper {

    fun changeColorItemClick(
        item: View,
        drawableOn: Drawable,
        drawableOff: Drawable,
        duration: Int = 1500
    ) {
        val backgrounds = arrayOfNulls<Drawable>(2)
        backgrounds[0] = drawableOn
        backgrounds[1] = drawableOff

        val transitionDrawable = TransitionDrawable(backgrounds)
        transitionDrawable.isCrossFadeEnabled = true
        item.background = transitionDrawable
        transitionDrawable.startTransition(duration)
    }
}