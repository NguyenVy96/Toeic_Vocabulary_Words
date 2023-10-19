package com.vynguyen.toeicvocabularywords.vocabulary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.utils.ColorHelper
import com.vynguyen.toeicvocabularywords.utils.Words
import java.lang.ref.WeakReference

class VocabularyAdapter(private var context: Context, private val dataList: List<Vocabulary>?) :
    RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>() {

    private var clickListener = WeakReference<VocabularyItemClickListener>(null)
    private var drawableOn: Drawable? = null
    private var drawableOff: Drawable? = null

    class VocabularyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemRootView: LinearLayout
        val itemLayout: ConstraintLayout
        val imgVocabulary: ImageView
        val tvWord: TextView
        val tvPhonetic: TextView
        val tvMean: TextView

        init {
            itemRootView = itemView.findViewById(R.id.vocabulary_item_root_view)
            itemLayout = itemView.findViewById(R.id.vocabulary_container)
            imgVocabulary = itemView.findViewById(R.id.img_vocabulary)
            tvPhonetic = itemView.findViewById(R.id.tv_phonetic)
            tvWord = itemView.findViewById(R.id.tv_word)
            tvMean = itemView.findViewById(R.id.tv_mean)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.vocabulary_item, parent, false)
        val res = context.resources
        drawableOn = res.getDrawable(R.drawable.item_on, null)
        drawableOff = res.getDrawable(R.drawable.item_off, null)

        return VocabularyViewHolder(rootView)
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
        holder.imgVocabulary.setImageResource(vocabulary.imgResource)
        holder.tvWord.text = vocabulary.word
        holder.tvPhonetic.text =
            vocabulary.phonetic + " " + Words.getWordStringType(vocabulary.type)
        holder.tvMean.text = Words.getStringFromList(vocabulary.mean)

        holder.itemLayout.setOnClickListener {
            clickListener.get()?.onVocabularyItemClick(vocabulary)
            ColorHelper.changeColorItemClick(it, drawableOn!!, drawableOff!!)
        }

        if (position == dataList.size - 1) {
            setBigPadding(holder.itemRootView)
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