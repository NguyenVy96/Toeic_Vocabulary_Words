package com.vynguyen.toeicvocabularywords.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.data.Vocabulary
import com.vynguyen.toeicvocabularywords.databinding.VocabularyItemBinding
import com.vynguyen.toeicvocabularywords.interfaces.VocabularyItemClickListener
import com.vynguyen.toeicvocabularywords.utils.ColorHelper
import com.vynguyen.toeicvocabularywords.utils.Words
import java.lang.ref.WeakReference

class VocabularyAdapter(private var context: Context, private val dataList: List<Vocabulary>?) :
    RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>() {

    private var clickListener = WeakReference<VocabularyItemClickListener>(null)
    private var drawableOn: Drawable? = null
    private var drawableOff: Drawable? = null

    class VocabularyViewHolder(vocabularyItemBinding: VocabularyItemBinding) :
        RecyclerView.ViewHolder(vocabularyItemBinding.root) {
        val viewBinding: VocabularyItemBinding = vocabularyItemBinding
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyViewHolder {
        val res = context.resources
        drawableOn = res.getDrawable(R.drawable.item_on, null)
        drawableOff = res.getDrawable(R.drawable.item_off, null)

        val vocabularyItemBinding: VocabularyItemBinding = VocabularyItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VocabularyViewHolder(vocabularyItemBinding)
    }

    override fun getItemCount(): Int {
        if (dataList == null) {
            return 0
        }
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VocabularyViewHolder, position: Int) {
        if (dataList.isNullOrEmpty()) {
            return
        }

        val vocabulary = dataList[position]
        holder.viewBinding.imgVocabulary.setImageResource(vocabulary.imgResource)
        holder.viewBinding.tvWord.text = vocabulary.word
        holder.viewBinding.tvPhonetic.text =
            vocabulary.phonetic + " " + Words.getWordStringType(vocabulary.type)
        holder.viewBinding.tvMean.text = Words.getStringFromList(vocabulary.mean)

        holder.viewBinding.vocabularyContainer.setOnClickListener {
            clickListener.get()?.onVocabularyItemClick(vocabulary)
            ColorHelper.changeColorItemClick(it, drawableOn!!, drawableOff!!)
        }

        if (position == dataList.size - 1) {
            setBigPadding(holder.viewBinding.vocabularyItemRootView)
        }
    }

    private fun setBigPadding(view: View) {
        val lp = view.layoutParams as RecyclerView.LayoutParams
        lp.bottomMargin =
            context.resources.getDimensionPixelSize(R.dimen.last_vocabulary_item_bottom_margin)
        view.layoutParams = lp
    }

    fun addClickListener(listener: VocabularyItemClickListener) {
        this.clickListener = WeakReference(listener)
    }

    fun onDestroy() {
        drawableOn = null
        drawableOff = null
    }
}