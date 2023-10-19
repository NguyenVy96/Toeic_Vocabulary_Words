package com.vynguyen.toeicvocabularywords.interfa

import com.vynguyen.toeicvocabularywords.data.Topic


interface TopicItemClickListener {

    fun onTopicItemClick(topic: Topic)
}