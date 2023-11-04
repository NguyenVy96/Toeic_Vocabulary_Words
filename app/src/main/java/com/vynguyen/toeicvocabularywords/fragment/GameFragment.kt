package com.vynguyen.toeicvocabularywords.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vynguyen.toeicvocabularywords.constant.IntentConstant.Companion.CONNECT_WORDS_GAME_ACTIVITY
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.databinding.FragmentGameBinding
import com.vynguyen.toeicvocabularywords.utils.PrefHelper
import com.vynguyen.toeicvocabularywords.utils.Utils

class GameFragment : Fragment() {

    private lateinit var viewBinding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentGameBinding.inflate(layoutInflater)

        setUpConnectWordsGame()

        viewBinding.choiceGameLayout.setOnClickListener {
            // TODO: start intent to Choice game
        }
        observerScore()


        return viewBinding.root
    }

    private fun observerScore() {
        val curTopic = TopicData.getLearningTopic()
        val curScore = Utils.getCurTopicScoreLiveData()
        curScore.value = PrefHelper.getTotalScore(curTopic)
        curScore.observe(this) {
            updateConnectWordsGameStar(
                PrefHelper.getConnectWordsGameScore(TopicData.getLearningTopic())
            )
        }
    }

    private fun setUpConnectWordsGame() {
        viewBinding.connectGameLayout.setOnClickListener {
            val intent = Intent(CONNECT_WORDS_GAME_ACTIVITY)
            startActivity(intent)
        }
        updateConnectWordsGameStar(
            PrefHelper.getConnectWordsGameScore(TopicData.getLearningTopic())
        )
    }

    private fun updateConnectWordsGameStar(starScore: Int) {
        viewBinding.imgConnectGameRate
            .setImageResource(Utils.getStarScoreImgResource(starScore))
    }
}