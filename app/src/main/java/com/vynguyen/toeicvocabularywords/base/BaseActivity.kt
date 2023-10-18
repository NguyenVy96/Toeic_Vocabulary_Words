package com.vynguyen.toeicvocabularywords.base

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.vynguyen.toeicvocabularywords.R

open class BaseActivity : AppCompatActivity() {

    fun onCreate() {
        setupToolbar()
        setupNaviDrawer()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupNaviDrawer() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_navi_drawer,
            R.string.close_navi_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

}