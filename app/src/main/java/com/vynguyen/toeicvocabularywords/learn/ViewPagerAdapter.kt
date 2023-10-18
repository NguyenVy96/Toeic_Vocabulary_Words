package com.vynguyen.toeicvocabularywords.learn

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.exam.ExamFragment
import com.vynguyen.toeicvocabularywords.game.GameFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return Constant.LEARN_ACTIVITY_FRAGMENT_NUMBER
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Constant.LEARN_FRAGMENT -> LearnFragment()
            Constant.EXAM_FRAGMENT -> ExamFragment()
            Constant.GAME_FRAGMENT -> GameFragment()
            else -> LearnFragment()
        }
    }

}