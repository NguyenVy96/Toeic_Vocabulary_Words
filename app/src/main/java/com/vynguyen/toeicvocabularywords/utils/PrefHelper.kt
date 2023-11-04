package com.vynguyen.toeicvocabularywords.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.vynguyen.toeicvocabularywords.constant.Constant

object PrefHelper {

    private lateinit var appSharedPreferences: SharedPreferences

    fun initAppSharedPref(activity: Activity): SharedPreferences {
        if (!this::appSharedPreferences.isInitialized) {
            appSharedPreferences = activity.getSharedPreferences(Constant.APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
        }
        return appSharedPreferences
    }

    fun setConnectWordsGameScore(topic: Int, score: Int) {
        with(appSharedPreferences.edit()) {
            putInt(getConnectWordsGameKey(topic), score)
            apply()
        }
    }

    fun getTotalScore(topic: Int): Int {
        return getConnectWordsGameScore(topic) + getChoiceWordsGameScore(topic)
    }

    fun getConnectWordsGameScore(topic: Int): Int {
        return appSharedPreferences.getInt(getConnectWordsGameKey(topic), 0)
    }

    fun setChoiceWordsGameScore(topic: Int, score: Int) {
        with(appSharedPreferences.edit()) {
            putInt(getChoiceWordsGameKey(topic), score)
            apply()
        }
    }

    fun getChoiceWordsGameScore(topic: Int): Int {
        return appSharedPreferences.getInt(getChoiceWordsGameKey(topic), 0)
    }

    private fun getConnectWordsGameKey(topic: Int): String {
        return topic.toString() + "_" + Constant.CONNECT_WORDS_GAME_STAR_SCORE_KEY
    }

    private fun getChoiceWordsGameKey(topic: Int): String {
        return topic.toString() + "_" + Constant.CHOICE_WORDS_GAME_STAR_SCORE_KEY
    }
}