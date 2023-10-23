package com.vynguyen.toeicvocabularywords.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.constant.IntentConstant.Companion.CONNECT_WORDS_GAME_ACTIVITY

class GameFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_game, container, false)

        setUpConnectWordsGame(rootView)

        val choiceGameLayout = rootView.findViewById<View>(R.id.choice_game_layout)
        choiceGameLayout.setOnClickListener {
            // TODO: start intent to Choice game
        }


        return rootView
    }

    override fun onResume() {
        super.onResume()
        updateConnectWordsGameStar(
            rootView,
            getStartScore(Constant.CONNECT_WORDS_GAME_WRONG_COUNT_KEY)
        )
    }

    private fun setUpConnectWordsGame(rootView: View) {
        val connectGameLayout = rootView.findViewById<View>(R.id.connect_game_layout)
        connectGameLayout.setOnClickListener {
            val intent = Intent(CONNECT_WORDS_GAME_ACTIVITY)
            startActivity(intent)
        }
        updateConnectWordsGameStar(
            rootView,
            getStartScore(Constant.CONNECT_WORDS_GAME_WRONG_COUNT_KEY)
        )
    }

    private fun updateConnectWordsGameStar(rootView: View, starScore: Int) {
        val imgResource = when (starScore) {
            0 -> R.drawable.ic_star_0
            1 -> R.drawable.ic_star_1
            2 -> R.drawable.ic_star_2
            3 -> R.drawable.ic_star_3
            4 -> R.drawable.ic_star_4
            5 -> R.drawable.ic_star_5
            else -> R.drawable.ic_star_0
        }
        rootView.findViewById<ImageView>(R.id.img_connect_game_rate).setImageResource(imgResource)
    }

    private fun getStartScore(key: String): Int {
        val sharedPreferences =
            activity?.getSharedPreferences(Constant.APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
        var wrongCount = Constant.NOT_LEARN
        if (sharedPreferences != null) {
            wrongCount = sharedPreferences.getInt(key, Constant.NOT_LEARN)
        }

        return if (wrongCount == Constant.NOT_SELECTED || wrongCount > 5) {
            0
        } else {
            5 - wrongCount
        }
    }
}