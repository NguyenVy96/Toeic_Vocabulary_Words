package com.vynguyen.toeicvocabularywords.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vynguyen.toeicvocabularywords.adapter.VocabularyAdapter
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.data.VocabularyData
import com.vynguyen.toeicvocabularywords.databinding.FragmentLearnBinding
import com.vynguyen.toeicvocabularywords.interfaces.VocabularyItemClickListener
import com.vynguyen.toeicvocabularywords.utils.SoundHelper


class LearnFragment : Fragment(), VocabularyItemClickListener {

    private lateinit var context: Context
    private lateinit var viewBinding: FragmentLearnBinding
    private var rcvAdapter: VocabularyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // get context from activity instead of transfer via parameter constructor to avoid FC when
        // change theme
        context = activity?.applicationContext!!

        viewBinding = FragmentLearnBinding.inflate(layoutInflater)
        val listData = VocabularyData.getVocabularyData(TopicData.getLearningTopic())
        rcvAdapter = VocabularyAdapter(context, listData)
        rcvAdapter?.addClickListener(this)
        val rcvVocabulary = viewBinding.rcvVocabulary
        rcvVocabulary.adapter = rcvAdapter
        rcvVocabulary.layoutManager = LinearLayoutManager(context)

        return viewBinding.root
    }

    override fun onVocabularyItemClick(vocabulary: Vocabulary) {
        SoundHelper.playSound(context, vocabulary.soundResource)
    }

    override fun onDestroy() {
        super.onDestroy()
        rcvAdapter?.onDestroy()
        rcvAdapter = null
    }
}