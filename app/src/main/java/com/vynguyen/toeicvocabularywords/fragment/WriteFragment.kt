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
import com.vynguyen.toeicvocabularywords.utils.Words


class WriteFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var imgResult: ImageView
    private lateinit var imgSpeaker: ImageView
    private lateinit var tvMean: TextView
    private lateinit var tvAnswer: TextView
    private lateinit var edtWrite: EditText
    private lateinit var btnSubmit: ToggleButton

    private var listData: MutableList<Vocabulary>? = null
    private var mediaPlayer: MediaPlayer? = null

    private var SPEAKER = 0
    private var MEAN = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_write, container, false)
        initView()
        showExam()

        return rootView
    }

    private fun initView() {
        imgResult = rootView.findViewById(R.id.img_result)
        imgSpeaker = rootView.findViewById(R.id.img_speaker)
        tvMean = rootView.findViewById(R.id.tv_mean)
        tvAnswer = rootView.findViewById(R.id.tv_answer)
        edtWrite = rootView.findViewById(R.id.edt_write)
        btnSubmit = rootView.findViewById(R.id.btn_submit)
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
        imgResult.visibility = View.INVISIBLE
        tvAnswer.visibility = View.GONE
        edtWrite.setText("")

        val vocabulary = getRandomVocabulary()

        when ((SPEAKER..MEAN).random()) {
            SPEAKER -> showSpeakerType(vocabulary)
            MEAN -> showMeanType(vocabulary)
        }

        setupSubmitButton(vocabulary)
    }

    private fun showSpeakerType(vocabulary: Vocabulary) {
        imgSpeaker.visibility = View.VISIBLE
        tvMean.visibility = View.INVISIBLE

        imgSpeaker.setOnClickListener {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
            }
            mediaPlayer = MediaPlayer.create(context, vocabulary.soundResource)
            mediaPlayer!!.start()
        }
    }

    private fun showMeanType(vocabulary: Vocabulary) {
        imgSpeaker.visibility = View.INVISIBLE
        tvMean.visibility = View.VISIBLE

        tvMean.text = Words.getStringFromList(vocabulary.mean)
    }

    private fun showResult(vocabulary: Vocabulary) {
        val userAnswer = edtWrite.text.toString().trim()
        if (userAnswer.isEmpty()) {
            Toast.makeText(context, R.string.pls_enter_text, Toast.LENGTH_SHORT).show()
            btnSubmit.isChecked = false
            return
        }

        if (userAnswer == vocabulary.word) {
            imgResult.setImageResource(R.drawable.ic_correct)
            mediaPlayer = MediaPlayer.create(context, R.raw.correct_answer)
            mediaPlayer!!.start()
        } else {
            imgResult.setImageResource(R.drawable.ic_wrong)
            tvAnswer.text = vocabulary.word
            tvAnswer.visibility = View.VISIBLE
            mediaPlayer = MediaPlayer.create(context, R.raw.wrong_answer)
            mediaPlayer!!.start()
        }

        imgResult.visibility = View.VISIBLE
    }

    private fun setupSubmitButton(vocabulary: Vocabulary) {
        btnSubmit.setOnClickListener {
            if (btnSubmit.isChecked) {
                showResult(vocabulary)
            }
            else {
                showExam()
            }
        }
    }
}