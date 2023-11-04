package com.vynguyen.toeicvocabularywords.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.adapter.TopicAdapter
import com.vynguyen.toeicvocabularywords.base.BaseActivity
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.constant.IntentConstant
import com.vynguyen.toeicvocabularywords.data.Topic
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.databinding.ActivityHomeBinding
import com.vynguyen.toeicvocabularywords.interfaces.TopicItemClickListener
import com.vynguyen.toeicvocabularywords.utils.PrefHelper
import com.vynguyen.toeicvocabularywords.utils.Utils


class HomeActivity() : BaseActivity(), TopicItemClickListener {

    private lateinit var viewBinding: ActivityHomeBinding

    private var topicAdapter: TopicAdapter? = null
    private var topicListData: MutableList<Topic>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        onCreate()
        PrefHelper.initAppSharedPref(this)
        setupRecycleView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun setupRecycleView() {
        topicListData = TopicData.getTopicData()
        topicAdapter =
            TopicAdapter(applicationContext, topicListData)
        topicAdapter?.addItemCLickListener(this)
        viewBinding.rcvTopic.layoutManager = LinearLayoutManager(this)
        viewBinding.rcvTopic.adapter = topicAdapter
    }

    override fun onTopicItemClick(topic: Topic) {
        val intent = Intent(IntentConstant.LEARN_ACTIVITY)
        intent.putExtra(Constant.TOPIC_KEY, topic.id)
        TopicData.setLearningTopic(topic.id)
        observerScore()
        startActivity(intent)
    }

    private fun observerScore() {
        val curTopic = TopicData.getLearningTopic()
        val curScore = Utils.getCurTopicScoreLiveData()
        curScore.value = PrefHelper.getTotalScore(curTopic)
        curScore.observe(this) {
            topicAdapter?.notifyItemChanged(curTopic)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearData()
    }

    private fun clearData() {
        topicListData = null
        topicAdapter?.onDestroy()
        topicAdapter = null
    }
}