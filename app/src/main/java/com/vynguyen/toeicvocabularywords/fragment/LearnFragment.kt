package com.vynguyen.toeicvocabularywords.fragment

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.data.TopicData
import com.vynguyen.toeicvocabularywords.data.VocabularyData
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.adapter.VocabularyAdapter
import com.vynguyen.toeicvocabularywords.interfaces.VocabularyItemClickListener


class LearnFragment : Fragment(), VocabularyItemClickListener {

    private lateinit var context: Context
    private var mediaPlayer: MediaPlayer? = null
    private var rcvAdapter: VocabularyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // get context from activity instead of transfer via parameter constructor to avoid FC when
        // change theme
        context = activity?.applicationContext!!
        val rootView = inflater.inflate(R.layout.fragment_learn, container, false)
        val listData = VocabularyData.getVocabularyData(TopicData.getLearningTopic())
        rcvAdapter = VocabularyAdapter(context, listData)
        rcvAdapter?.addClickListener(this)
        val rcvVocabulary = rootView.findViewById<RecyclerView>(R.id.rcv_vocabulary)
        rcvVocabulary.adapter = rcvAdapter
        rcvVocabulary.layoutManager = LinearLayoutManager(context)

        return rootView
    }

    override fun onVocabularyItemClick(vocabulary: Vocabulary) {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        }
        mediaPlayer = MediaPlayer.create(context, vocabulary.soundResource)
        mediaPlayer!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        rcvAdapter?.onDestroy()
        rcvAdapter = null
        if (mediaPlayer != null) {
            mediaPlayer!!.pause()
            mediaPlayer!!.release()
        }
    }
}