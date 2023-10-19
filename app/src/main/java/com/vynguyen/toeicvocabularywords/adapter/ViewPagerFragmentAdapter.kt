package com.vynguyen.toeicvocabularywords.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.fragment.ExamFragment
import com.vynguyen.toeicvocabularywords.fragment.LearnFragment
import com.vynguyen.toeicvocabularywords.fragment.GameFragment
import com.vynguyen.toeicvocabularywords.fragment.SpeakFragment
import com.vynguyen.toeicvocabularywords.fragment.WriteFragment

class ViewPagerFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return Constant.LEARN_ACTIVITY_FRAGMENT_NUMBER
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Constant.LEARN_FRAGMENT -> LearnFragment()
            Constant.WRITE_FRAGMENT -> WriteFragment()
            Constant.SPEAK_FRAGMENT -> SpeakFragment()
            Constant.EXAM_FRAGMENT -> ExamFragment()
            Constant.GAME_FRAGMENT -> GameFragment()
            else -> LearnFragment()
        }
    }

}