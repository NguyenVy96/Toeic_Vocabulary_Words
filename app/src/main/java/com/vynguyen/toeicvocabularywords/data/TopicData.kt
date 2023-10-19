package com.vynguyen.toeicvocabularywords.data


import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.topic.Topic

object TopicData {

    private var learningTopic: Int = 0

    fun getTopicData(): MutableList<Topic> {
        val data: MutableList<Topic> = mutableListOf()
        for (i: Int in 0..50) {
            data.add(Topic(i, R.drawable.ic_dictionary, "Contrast $i"))
        }
        return data
    }

    fun setLearningTopic(topic: Int) {
        learningTopic = topic
    }

    fun getLearningTopic(): Int {
        return learningTopic
    }
}