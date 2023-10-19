package com.vynguyen.toeicvocabularywords.interfa

import com.vynguyen.toeicvocabularywords.data.Vocabulary

interface VocabularyItemClickListener {

    fun onVocabularyItemClick(vocabulary: Vocabulary)
}