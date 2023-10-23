package com.vynguyen.toeicvocabularywords.interfaces

import com.vynguyen.toeicvocabularywords.data.Vocabulary

interface VocabularyItemClickListener {

    fun onVocabularyItemClick(vocabulary: Vocabulary)
}