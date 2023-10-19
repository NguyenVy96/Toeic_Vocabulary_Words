package com.vynguyen.toeicvocabularywords.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.interfa.TopicItemClickListener
import com.vynguyen.toeicvocabularywords.data.Topic
import com.vynguyen.toeicvocabularywords.utils.ColorHelper
import java.lang.ref.WeakReference


class TopicAdapter(
    private val context: Context,
    private val listData: MutableList<Topic>?
) :
    RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    private var itemClickListener = WeakReference<TopicItemClickListener>(null)
    private var drawableOn: Drawable? = null
    private var drawableOff: Drawable? = null

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout: ConstraintLayout
        val imgTopic: ImageView
        val tvTopicName: TextView
        val tvVoNumber: TextView
        val tvScore: TextView
        val pgbScore: ProgressBar

        init {
            itemLayout = itemView.findViewById(R.id.topic_container)
            imgTopic = itemView.findViewById(R.id.img_topic)
            tvTopicName = itemView.findViewById(R.id.tv_topic_name)
            tvVoNumber = itemView.findViewById(R.id.tv_vocabulary_number)
            tvScore = itemView.findViewById(R.id.tv_score)
            pgbScore = itemView.findViewById(R.id.pgb_score)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        val res = context.resources
        drawableOn = res.getDrawable(R.drawable.item_on, null)
        drawableOff = res.getDrawable(R.drawable.item_off, null)

        return TopicViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (listData == null) {
            return 0
        }
        return listData.size
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        if (listData.isNullOrEmpty()) {
            return
        }

        val topic = listData[position]
        val res = context.resources
        holder.imgTopic.setImageResource(topic.imgResource)
        holder.tvTopicName.text = res.getString(R.string.topic) + " " + position + ": " + topic.name
        holder.tvVoNumber.text =
            res.getString(R.string.vocabulary) + ": " + topic.vocabularyNumber
        holder.tvScore.text = topic.score.toString() + "%"
        holder.pgbScore.progress = topic.score
        holder.itemLayout.setOnClickListener {
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