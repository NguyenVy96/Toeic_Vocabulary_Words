package com.vynguyen.toeicvocabularywords.learn

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.base.BaseActivity
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.databinding.ActivityLearnBinding

class LearnActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityLearnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        onCreate()
        setupViewPager()
        connectViewPagerWithBottomNavi()
    }

    private fun setupViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewBinding.viewpager.adapter = viewPagerAdapter
    }

    private fun connectViewPagerWithBottomNavi() {
        val bottomNavi = viewBinding.bottomNaviContainer.bottomNavi
        val viewPager = viewBinding.viewpager

        viewPager.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    Constant.LEARN_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_learn).isChecked = true

                    Constant.EXAM_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_exam).isChecked = true

                    Constant.GAME_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_game).isChecked = true
                }
            }
        })

        bottomNavi.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_learn -> viewPager.currentItem = 0
                R.id.menu_exam -> viewPager.currentItem = 1
                R.id.menu_game -> viewPager.currentItem = 2
                else -> viewPager.currentItem = 0
            }
            return@setOnItemSelectedListener true
        }

    }
}