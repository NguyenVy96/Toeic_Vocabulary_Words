package com.vynguyen.toeicvocabularywords.data


import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.topic.Topic

object TopicData {

    fun getTopicData(): MutableList<Topic> {
        val data: MutableList<Topic> = mutableListOf()
        for (i: Int in 0..50) {
            data.add(Topic(R.drawable.ic_dictionary, "Contrast $i"))
        }
        return data
    }
}