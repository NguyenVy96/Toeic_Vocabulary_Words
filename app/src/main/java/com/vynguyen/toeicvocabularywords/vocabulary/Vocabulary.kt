package com.vynguyen.toeicvocabularywords.vocabulary

data class Vocabulary(
    val imgResource: Int,
    val soundResource: Int,
    val word: String,
    val phonetic: String,
    val type: Int,
    val mean: List<String>
)