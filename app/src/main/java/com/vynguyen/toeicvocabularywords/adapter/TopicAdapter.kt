package com.vynguyen.toeicvocabularywords.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.data.Topic
import com.vynguyen.toeicvocabularywords.databinding.TopicItemBinding
import com.vynguyen.toeicvocabularywords.interfaces.TopicItemClickListener
import com.vynguyen.toeicvocabularywords.utils.ColorHelper
import com.vynguyen.toeicvocabularywords.utils.PrefHelper
import java.lang.ref.WeakReference


class TopicAdapter(
    private val context: Context,
    private val listData: MutableList<Topic>?,
) :
    RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    private var itemClickListener = WeakReference<TopicItemClickListener>(null)
    private var drawableOn: Drawable? = null
    private var drawableOff: Drawable? = null

    class TopicViewHolder(topicItemBinding: TopicItemBinding) :
        RecyclerView.ViewHolder(topicItemBinding.root) {
        val viewBinding: TopicItemBinding = topicItemBinding
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val topicItemBinding: TopicItemBinding =
            TopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val res = context.resources
        drawableOn = res.getDrawable(R.drawable.item_on, null)
        drawableOff = res.getDrawable(R.drawable.item_off, null)

        return TopicViewHolder(topicItemBinding)
    }

    override fun getItemCount(): Int {
        if (listData.isNullOrEmpty()) {
            return 0
        }
        return listData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        if (listData.isNullOrEmpty()) {
            return
        }

        val topic = listData[position]
        val res = context.resources
        val score = PrefHelper.getTotalScore(position)

        holder.viewBinding.imgTopic.setImageResource(topic.imgResource)
        holder.viewBinding.tvTopicName.text =
            res.getString(R.string.topic) + " " + position + ": " + topic.name
        holder.viewBinding.tvVocabularyNumber.text =
            res.getString(R.string.vocabulary) + ": " + topic.vocabularyNumber
        holder.viewBinding.tvScore.text = (score * 10).toString() + "%"
        holder.viewBinding.pgbScore.max = 10
        holder.viewBinding.pgbScore.progress = score

        holder.viewBinding.topicContainer.setOnClickListener {
            itemClickListener.get()?.onTopicItemClick(topic)
            ColorHelper.changeColorItemClick(it, drawableOn!!, drawableOff!!)
        }
    }

    fun addItemCLickListener(listener: TopicItemClickListener) {
        itemClickListener = WeakReference(listener)
    }

    fun onDestroy() {
        drawableOn = null
        drawableOff = null
    }
}