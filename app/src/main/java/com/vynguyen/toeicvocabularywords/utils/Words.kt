package com.vynguyen.toeicvocabularywords.utils

object Words {

    const val N = 0
    const val V = 1
    const val ADJ = 2
    const val ADV = 3

    fun getWordStringType(type: Int): String {
        return when (type) {
            N -> "(n)"
            V -> "(v)"
            ADJ -> "(adj)"
            ADV -> "(adv)"
            else -> "(n)"
        }
    }

    fun getStringFromList(list: List<String>): String {
        var result = ""
        list.forEach {
            result = if (result.isEmpty()) {
                it
            } else {
                "$result, $it"
            }
        }
        return result
    }
}