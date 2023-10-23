package com.vynguyen.toeicvocabularywords.presenter

import android.content.Context
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.constant.Constant.Companion.NOT_SELECTED
import com.vynguyen.toeicvocabularywords.constant.Constant.Companion.SESSION_NUMBER
import com.vynguyen.toeicvocabularywords.constant.Constant.Companion.WORDS_NUMBER
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.data.VocabularyData
import com.vynguyen.toeicvocabularywords.interfaces.ConnectWordsGameInterface
import com.vynguyen.toeicvocabularywords.utils.SoundHelper
import java.lang.ref.WeakReference

class ConnectWordsGamePresenter(private val context: Context) {

    private var session: Int = 0
    private var wrongCount = 0
    private var remainingItem = 0
    private var vIndex: Int = NOT_SELECTED
    private var eIndex: Int = NOT_SELECTED
    private lateinit var vData: MutableList<Vocabulary>
    private lateinit var eData: MutableList<Vocabulary>
    private var listData: MutableList<Vocabulary>? = null

    private var listener = WeakReference<ConnectWordsGameInterface>(null)

    fun addListener(listener: ConnectWordsGameInterface) {
        this.listener = WeakReference(listener)
    }

    fun onClick(value: Int) {
        if (value == NOT_SELECTED) {
            return
        }

        setSelectedColor(value)
        if (eIndex != NOT_SELECTED && vIndex != NOT_SELECTED) {
            showResult()
        }
    }

    private fun setSelectedColor(value: Int) {
        if (value > WORDS_NUMBER) {
            if (eIndex != NOT_SELECTED) {
                listener.get()?.setSelectedColor(value, eIndex + 10)
            } else {
                listener.get()?.setSelectedColor(value, NOT_SELECTED)
            }
            eIndex = value % Constant.DIV
        } else {
            if (vIndex != NOT_SELECTED) {
                listener.get()?.setSelectedColor(value, vIndex)
            } else {
                listener.get()?.setSelectedColor(value, NOT_SELECTED)
            }
            vIndex = value
        }
    }

    private fun correctAnswer() {
        SoundHelper.playCorrectSound(context)
        listener.get()?.correctAnswer(vIndex, eIndex + 10)
        resetSelection()
        remainingItem--
    }

    private fun resetSelection() {
        eIndex = NOT_SELECTED
        vIndex = NOT_SELECTED
    }

    private fun wrongAnswer() {
        wrongCount++
        SoundHelper.playWrongSound(context)
        listener.get()?.wrongAnswer(wrongCount, vIndex, eIndex + 10)
        resetSelection()
    }

    private fun showResult() {
        if (vData.isEmpty() || eData.isEmpty()) {
            return
        }

        if (vData[vIndex].word == eData[eIndex].word) {
            correctAnswer()
        } else {
            wrongAnswer()
        }
    }

    fun loadNewSession() {
        if (remainingItem != 0) {
            return
        }

        remainingItem = 4
        session++
        if (session > SESSION_NUMBER) {
            listener.get()?.saveData(wrongCount)
            showResultDialog()
            return
        }

        if (listData == null || listData!!.size < WORDS_NUMBER - 1) {
            listData =
                VocabularyData.getVocabularyData(TopicData.getLearningTopic()).toMutableList()
        }

        vData = mutableListOf<Vocabulary>()
        for (count in 1..4) {
            val index = (0..11).random() % listData!!.size
            val vocabulary = listData!![index]
            vData.add(vocabulary)
            listData!!.removeAt(index)
        }

        eData = mutableListOf<Vocabulary>()
        val vDataCopy: MutableList<Vocabulary> = vData.toMutableList()
        for (count in 1..4) {
            val index = (0..3).random() % vDataCopy.size
            val vocabulary = vDataCopy[index]
            eData.add(vocabulary)
            vDataCopy.removeAt(index)
        }

        listener.get()?.loadNewSession(vData, eData, session)
    }

    private fun showResultDialog() {

    }
}