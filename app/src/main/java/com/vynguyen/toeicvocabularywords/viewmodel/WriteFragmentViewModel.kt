package com.vynguyen.toeicvocabularywords.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.data.VocabularyData
import com.vynguyen.toeicvocabularywords.utils.Words

class WriteFragmentViewModel : ViewModel() {

    companion object {
        private const val SPEAKER = 0
        private const val MEAN = 1
        private const val SHOW_EXAM = 2
        private const val SHOW_CORRECT_ANSWER = 3
        private const val SHOW_WRONG_ANSWER = 3
    }

    val state = MutableLiveData(SHOW_EXAM)
    val isBtnChecked = MutableLiveData(false)

    var isShowImgResult = MutableLiveData(false)
    var imgResultRes = R.drawable.ic_wrong

    var isShowImgSpeaker = MutableLiveData(false)

    var isShowTvMean = MutableLiveData(false)
    var tvMean = MutableLiveData("")

    var isShowCorrectAnswer = MutableLiveData(false)
    var tvCorrectAnswer = MutableLiveData("")

    var soundResult = R.raw.wrong_answer
    var userAnswer = MutableLiveData("")
    var isNeedShowToast = false

    private var listData: MutableList<Vocabulary>? = null
    var vocabulary = Vocabulary(0, 0, "", "", 0, listOf())

    private fun getRandomVocabulary(): Vocabulary {
        if (listData.isNullOrEmpty()) {
            listData =
                VocabularyData.getVocabularyData(TopicData.getLearningTopic()).toMutableList()
        }

        val randomValue = (0..11).random()
        val index = randomValue % listData!!.size
        val vocabulary = listData!![index]
        listData!!.removeAt(index)

        return vocabulary
    }

    fun showExam() {
        vocabulary = getRandomVocabulary()

        isShowImgResult.value = false
        isShowCorrectAnswer.value = false
        userAnswer.value = ""

        when ((SPEAKER..MEAN).random()) {
            SPEAKER -> showSpeakerExam()
            MEAN -> showMeanExam()
        }
    }

    private fun showSpeakerExam() {
        isShowImgSpeaker.value = true
        isShowTvMean.value = false
    }

    private fun showMeanExam() {
        isShowImgSpeaker.value = false
        isShowTvMean.value = true
        tvMean.value = Words.getStringFromList(vocabulary.mean)
    }

    private fun showCorrectAnswer() {
        imgResultRes = R.drawable.ic_correct
        isShowCorrectAnswer.value = false
        soundResult = R.raw.correct_answer
        state.value = SHOW_CORRECT_ANSWER
    }

    private fun showWrongAnswer() {
        imgResultRes = R.drawable.ic_wrong
        isShowCorrectAnswer.value = true
        tvCorrectAnswer.value = vocabulary.word
        soundResult = R.raw.wrong_answer
        state.value = SHOW_WRONG_ANSWER
    }

    private fun showResult() {
        val answer = userAnswer.value.toString().trim()
        if (answer.isEmpty()) {
            isNeedShowToast = true
            state.value = SHOW_EXAM
            return
        }

        isShowImgResult.value = true
        if (answer == vocabulary.word) {
            showCorrectAnswer()
        } else {
            showWrongAnswer()
        }
    }

    fun onBtnSubmitClick() {
        isNeedShowToast = false
        if (isBtnChecked.value == false) {
            showResult()
            isBtnChecked.value = isNeedShowToast != true
        } else {
            showExam()
            isBtnChecked.value = false
        }
    }

    fun isCanPlaySoundResult(): Boolean {
        return state.value != SHOW_EXAM
    }

}