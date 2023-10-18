package com.vynguyen.toeicvocabularywords.topic

data class Topic(
    val id: Int,
    val imgResource: Int,
    val name: String,
    val vocabularyNumber: Int = 12,
    val score: Int = 0
)
