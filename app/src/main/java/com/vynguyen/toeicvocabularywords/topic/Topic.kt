package com.vynguyen.toeicvocabularywords.topic

data class Topic(
    val imgResource: Int,
    val name: String,
    val vocabularyNumber: Int = 12,
    val score: Int = 0
)
