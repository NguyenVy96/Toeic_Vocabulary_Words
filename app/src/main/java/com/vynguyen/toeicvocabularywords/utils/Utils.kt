package com.vynguyen.toeicvocabularywords.utils

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.vynguyen.toeicvocabularywords.R


object Utils {

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setBottomNaviVisible(activity: Activity, visible: Int) {
        val bottomNaviContainer = activity.findViewById<View>(R.id.bottom_navi_container)
        bottomNaviContainer.visibility = visible
    }
}