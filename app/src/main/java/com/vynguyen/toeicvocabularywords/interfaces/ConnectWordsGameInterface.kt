package com.vynguyen.toeicvocabularywords.interfaces

import com.vynguyen.toeicvocabularywords.data.Vocabulary

interface ConnectWordsGameInterface {

    fun setSelectedColor(onIdx: Int, offIdx: Int)
    fun correctAnswer(vIdx: Int, eIdx: Int)
    fun wrongAnswer(starScore: Int, vIdx: Int, eIdx: Int)
    fun loadNewSession(vData: MutableList<Vocabulary>, eData: MutableList<Vocabulary>, session: Int)
    fun saveData(starScore: Int)
    fun showResultDialog(starScore: Int)
}