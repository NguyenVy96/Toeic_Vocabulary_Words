package com.vynguyen.toeicvocabularywords.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.base.BaseActivity
import com.vynguyen.toeicvocabularywords.constant.Constant
import com.vynguyen.toeicvocabularywords.databinding.ActivityLearnBinding
import com.vynguyen.toeicvocabularywords.adapter.ViewPagerFragmentAdapter

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
        val viewPagerAdapter = ViewPagerFragmentAdapter(this)
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

                    Constant.WRITE_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_write).isChecked = true

                    Constant.SPEAK_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_speak).isChecked = true

                    Constant.EXAM_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_exam).isChecked = true

                    Constant.GAME_FRAGMENT ->
                        bottomNavi.menu.findItem(R.id.menu_game).isChecked = true

                    else -> bottomNavi.menu.findItem(R.id.menu_learn).isChecked = true
                }
            }
        })

        bottomNavi.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_learn -> viewPager.currentItem = Constant.LEARN_FRAGMENT
                R.id.menu_write -> viewPager.currentItem = Constant.WRITE_FRAGMENT
                R.id.menu_speak -> viewPager.currentItem = Constant.SPEAK_FRAGMENT
                R.id.menu_exam -> viewPager.currentItem = Constant.EXAM_FRAGMENT
                R.id.menu_game -> viewPager.currentItem = Constant.GAME_FRAGMENT
                else -> viewPager.currentItem = Constant.LEARN_FRAGMENT
            }
            return@setOnItemSelectedListener true
        }

    }
}