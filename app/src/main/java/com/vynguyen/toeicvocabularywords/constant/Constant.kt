package com.vynguyen.toeicvocabularywords.constant

import androidx.viewpager2.widget.ViewPager2

class Constant {

    companion object {
        // fragment
        const val LEARN_ACTIVITY_FRAGMENT_NUMBER = 5
        const val LEARN_FRAGMENT = 0
        const val WRITE_FRAGMENT = 1
        const val SPEAK_FRAGMENT = 2
        const val EXAM_FRAGMENT = 3
        const val GAME_FRAGMENT = 4

        // key
        const val APP_PREFERENCE_KEY = "app_preference_key"
        const val TOPIC_KEY = "topic_key"
        const val USER_NAME_KEY = "user_name_key"
        const val CONNECT_WORDS_GAME_WRONG_COUNT_KEY = "connect_words_game_wrong_count_key"
        const val CONNECT_WORDS_GAME_PERCENT_SCORE = "connect_words_game_percent_score"

        // connect words game
        const val DIV = 10
        const val NOT_SELECTED = -1
        const val WORDS_NUMBER = 4
        const val V1 = 0
        const val V2 = 1
        const val V3 = 2
        const val V4 = 3
        const val E1 = 10
        const val E2 = 11
        const val E3 = 12
        const val E4 = 13
        const val SESSION_NUMBER = 2
        const val NOT_LEARN = -1
    }
}