package com.vynguyen.toeicvocabularywords.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.data.VocabularyData
import com.vynguyen.toeicvocabularywords.databinding.FragmentWriteBinding
import com.vynguyen.toeicvocabularywords.utils.Words


class WriteFragment : Fragment() {

    private lateinit var viewBinding: FragmentWriteBinding

    private var listData: MutableList<Vocabulary>? = null
    private var mediaPlayer: MediaPlayer? = null

    private var SPEAKER = 0
    private var MEAN = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentWriteBinding.inflate(layoutInflater)
        showExam()

        return viewBinding.root
    }

    private fun getRandomVocabulary(): Vocabulary {
        if (listData.isNullOrEmpty()) {
            listData =
                VocabularyData.getVocabularyData(TopicData.getLearningTopic())?.toMutableList()
        }

        val randomValue = (0..11).random()
        val index = randomValue % listData!!.size
        val vocabulary = listData!![index]
        listData!!.removeAt(index)

        return vocabulary
    }

    private fun showExam() {
        viewBinding.imgResult.visibility = View.INVISIBLE
        viewBinding.tvAnswer.visibility = View.GONE
        viewBinding.edtWrite.setText("")

        val vocabulary = getRandomVocabulary()

        when ((SPEAKER..MEAN).random()) {
            SPEAKER -> showSpeakerType(vocabulary)
            MEAN -> showMeanType(vocabulary)
        }

        setupSubmitButton(vocabulary)
    }

    private fun showSpeakerType(vocabulary: Vocabulary) {
        viewBinding.imgSpeaker.visibility = View.VISIBLE
        viewBinding.tvMean.visibility = View.INVISIBLE

        viewBinding.imgSpeaker.setOnClickListener {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
            }
            mediaPlayer = MediaPlayer.create(context, vocabulary.soundResource)
            mediaPlayer!!.start()
        }
    }

    private fun showMeanType(vocabulary: Vocabulary) {
        viewBinding.imgSpeaker.visibility = View.INVISIBLE
        viewBinding.tvMean.visibility = View.VISIBLE

        viewBinding.tvMean.text = Words.getStringFromList(vocabulary.mean)
    }

    private fun showResult(vocabulary: Vocabulary) {
        val userAnswer = viewBinding.edtWrite.text.toString().trim()
        if (userAnswer.isEmpty()) {
            Toast.makeText(context, R.string.pls_enter_text, Toast.LENGTH_SHORT).show()
            viewBinding.btnSubmit.isChecked = false
            return
        }

        if (userAnswer == vocabulary.word) {
            viewBinding.imgResult.setImageResource(R.drawable.ic_correct)
            mediaPlayer = MediaPlayer.create(context, R.raw.correct_answer)
            mediaPlayer!!.start()
        } else {
            viewBinding.imgResult.setImageResource(R.drawable.ic_wrong)
            viewBinding.tvAnswer.text = vocabulary.word
            viewBinding.tvAnswer.visibility = View.VISIBLE
            mediaPlayer = MediaPlayer.create(context, R.raw.wrong_answer)
            mediaPlayer!!.start()
        }

        viewBinding.imgResult.visibility = View.VISIBLE
    }

    private fun setupSubmitButton(vocabulary: Vocabulary) {
        viewBinding.btnSubmit.setOnClickListener {
            if (viewBinding.btnSubmit.isChecked) {
                showResult(vocabulary)
            }
            else {
                showExam()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listData = null
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
}