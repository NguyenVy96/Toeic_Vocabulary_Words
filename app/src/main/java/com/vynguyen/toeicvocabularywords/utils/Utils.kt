package com.vynguyen.toeicvocabularywords.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.vynguyen.toeicvocabularywords.R


object Utils {

    private val curTopicScore = MutableLiveData(0)
    private lateinit var appSharedPreferences: SharedPreferences

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

    fun getStarScoreImgResource(starScore: Int): Int {
        return when (starScore) {
            0 -> R.drawable.ic_star_0
            1 -> R.drawable.ic_star_1
            2 -> R.drawable.ic_star_2
            3 -> R.drawable.ic_star_3
            4 -> R.drawable.ic_star_4
            5 -> R.drawable.ic_star_5
            else -> R.drawable.ic_star_0
        }
    }

    fun getCurTopicScoreLiveData(): MutableLiveData<Int> {
        return curTopicScore
    }
}