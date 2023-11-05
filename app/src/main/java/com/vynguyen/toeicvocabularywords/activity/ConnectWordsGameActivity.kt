package com.vynguyen.toeicvocabularywords.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.databinding.ActivityConnectWordsGameBinding
import com.vynguyen.toeicvocabularywords.databinding.ConnectWordsGameBodyLayoutBinding
import com.vynguyen.toeicvocabularywords.databinding.ConnectWordsGameHeaderLayoutBinding
import com.vynguyen.toeicvocabularywords.interfaces.ConnectWordsGameInterface
import com.vynguyen.toeicvocabularywords.presenter.ConnectWordsGamePresenter
import com.vynguyen.toeicvocabularywords.utils.ColorHelper
import com.vynguyen.toeicvocabularywords.utils.DialogHelper
import com.vynguyen.toeicvocabularywords.utils.PrefHelper
import com.vynguyen.toeicvocabularywords.utils.SoundHelper
import com.vynguyen.toeicvocabularywords.utils.Utils
import com.vynguyen.toeicvocabularywords.utils.Words
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnectWordsGameActivity : AppCompatActivity(), ConnectWordsGameInterface {

    private lateinit var headerBinding: ConnectWordsGameHeaderLayoutBinding
    private lateinit var bodyBinding: ConnectWordsGameBodyLayoutBinding
    private lateinit var layoutBinding: ActivityConnectWordsGameBinding
    private lateinit var presenter: ConnectWordsGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutBinding = ActivityConnectWordsGameBinding.inflate(layoutInflater)
        headerBinding = layoutBinding.layoutConnectGameWordsHeader
        bodyBinding = ConnectWordsGameBodyLayoutBinding.bind(layoutBinding.root)
        setContentView(layoutBinding.root)

        bodyBinding.tvE1.setOnClickListener { onClick(it) }
        bodyBinding.tvE2.setOnClickListener { onClick(it) }
        bodyBinding.tvE3.setOnClickListener { onClick(it) }
        bodyBinding.tvE4.setOnClickListener { onClick(it) }
        bodyBinding.tvV1.setOnClickListener { onClick(it) }
        bodyBinding.tvV2.setOnClickListener { onClick(it) }
        bodyBinding.tvV3.setOnClickListener { onClick(it) }
        bodyBinding.tvV4.setOnClickListener { onClick(it) }

        presenter = ConnectWordsGamePresenter(applicationContext)
        presenter.addListener(this)
        presenter.loadNewSession()
    }

    private fun onClick(v: View?) {
        val value = when (v) {
            bodyBinding.tvE1 -> Constant.E1
            bodyBinding.tvE2 -> Constant.E2
            bodyBinding.tvE3 -> Constant.E3
            bodyBinding.tvE4 -> Constant.E4
            bodyBinding.tvV1 -> Constant.V1
            bodyBinding.tvV2 -> Constant.V2
            bodyBinding.tvV3 -> Constant.V3
            bodyBinding.tvV4 -> Constant.V4
            else -> Constant.NOT_SELECTED
        }
        presenter.onClick(value)
    }

    private fun getView(idx: Int): View? {
        return when (idx) {
            Constant.V1 -> bodyBinding.tvV1
            Constant.V2 -> bodyBinding.tvV2
            Constant.V3 -> bodyBinding.tvV3
            Constant.V4 -> bodyBinding.tvV4
            Constant.E1 -> bodyBinding.tvE1
            Constant.E2 -> bodyBinding.tvE2
            Constant.E3 -> bodyBinding.tvE3
            Constant.E4 -> bodyBinding.tvE4
            else -> null
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setSelectedColor(onIdx: Int, offIdx: Int) {
        val blueDrawable: Drawable =
            resources.getDrawable(R.drawable.rounded_blue_rectangle_20, null)
        val normalDrawable: Drawable =
            resources.getDrawable(R.drawable.rounded_rectangle_item_20, null)
        val duration = 500

        getView(onIdx)?.let {
            ColorHelper.changeColorItemClick(
                it,
                normalDrawable,
                blueDrawable,
                duration
            )
        }
        getView(offIdx)?.let {
            ColorHelper.changeColorItemClick(
                it,
                blueDrawable,
                normalDrawable,
                duration
            )
        }
    }

    override fun correctAnswer(vIdx: Int, eIdx: Int) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            setClickableAllWords(false)
            delay(500)
            onCorrectAnswer(vIdx, eIdx)
            setClickableAllWords(true)
            presenter.loadNewSession()
        }
    }

    private fun onCorrectAnswer(vIdx: Int, eIdx: Int) {
        resetSelectionColor(vIdx, eIdx)
        getView(vIdx)?.visibility = View.GONE
        getView(eIdx)?.visibility = View.GONE
    }

    override fun wrongAnswer(starScore: Int, vIdx: Int, eIdx: Int) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            setClickableAllWords(false)
            delay(500)
            onWrongAnswer(starScore, vIdx, eIdx)
            setClickableAllWords(true)
        }
    }

    private fun onWrongAnswer(starScore: Int, vIdx: Int, eIdx: Int) {
        when (starScore) {
            0 -> headerBinding.imgStart1.visibility = View.INVISIBLE
            1 -> headerBinding.imgStart2.visibility = View.INVISIBLE
            2 -> headerBinding.imgStart3.visibility = View.INVISIBLE
            3 -> headerBinding.imgStart4.visibility = View.INVISIBLE
            4 -> headerBinding.imgStart5.visibility = View.INVISIBLE
        }
        resetSelectionColor(vIdx, eIdx)
    }

    override fun loadNewSession(
        vData: MutableList<Vocabulary>,
        eData: MutableList<Vocabulary>,
        session: Int
    ) {
        setWordsText(vData, eData)
        showAllWords()
        updateSession(session)
    }

    override fun saveData(starScore: Int) {
        PrefHelper.setConnectWordsGameScore(TopicData.getLearningTopic(), starScore)
        Utils.getCurTopicScoreLiveData().value =
            PrefHelper.getTotalScore(TopicData.getLearningTopic())
    }

    override fun showResultDialog(starScore: Int) {
        SoundHelper.playClapSound(this)

        val dialog = DialogHelper.getDialog(this, R.layout.dialog_game_result)
        val imgStartScore = dialog.findViewById<ImageView>(R.id.img_star_score)
        imgStartScore.setImageResource(Utils.getStarScoreImgResource(starScore))

        val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }

        val scope = CoroutineScope(Dispatchers.Main)
        val job = scope.launch {
            delay(5000)
            dialog.dismiss()
        }
        job.start()

        dialog.setOnDismissListener {
            job.cancel()
            finish()
        }
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateSession(session: Int) {
        headerBinding.tvSession.text = session.toString() + "/" + Constant.SESSION_NUMBER
    }

    private fun setWordsText(vData: MutableList<Vocabulary>, eData: MutableList<Vocabulary>) {
        bodyBinding.tvV1.text = Words.getStringFromList(vData[0].mean)
        bodyBinding.tvV2.text = Words.getStringFromList(vData[1].mean)
        bodyBinding.tvV3.text = Words.getStringFromList(vData[2].mean)
        bodyBinding.tvV4.text = Words.getStringFromList(vData[3].mean)

        bodyBinding.tvE1.text = eData[0].word
        bodyBinding.tvE2.text = eData[1].word
        bodyBinding.tvE3.text = eData[2].word
        bodyBinding.tvE4.text = eData[3].word
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun resetSelectionColor(vIdx: Int, eIdx: Int) {
        val drawable = resources.getDrawable(R.drawable.rounded_rectangle_item_20, null)
        getView(vIdx)?.background = drawable
        getView(eIdx)?.background = drawable
    }

    private fun showAllWords() {
        bodyBinding.tvV1.visibility = View.VISIBLE
        bodyBinding.tvV2.visibility = View.VISIBLE
        bodyBinding.tvV3.visibility = View.VISIBLE
        bodyBinding.tvV4.visibility = View.VISIBLE

        bodyBinding.tvE1.visibility = View.VISIBLE
        bodyBinding.tvE2.visibility = View.VISIBLE
        bodyBinding.tvE3.visibility = View.VISIBLE
        bodyBinding.tvE4.visibility = View.VISIBLE
    }

    private fun setClickableAllWords(canClick: Boolean) {
        bodyBinding.tvV1.isClickable = canClick
        bodyBinding.tvV2.isClickable = canClick
        bodyBinding.tvV3.isClickable = canClick
        bodyBinding.tvV4.isClickable = canClick

        bodyBinding.tvE1.isClickable = canClick
        bodyBinding.tvE2.isClickable = canClick
        bodyBinding.tvE3.isClickable = canClick
        bodyBinding.tvE4.isClickable = canClick
    }

    private fun showExitGameDialog() {
        val dialog = DialogHelper.getDialog(this, R.layout.dialog_exit_game)

        val btnNo = dialog.findViewById<Button>(R.id.btn_no)
        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        val btnYes = dialog.findViewById<Button>(R.id.btn_yes)
        btnYes.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }

    override fun onBackPressed() {
        showExitGameDialog()
        // no need to call super
        if (false) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}