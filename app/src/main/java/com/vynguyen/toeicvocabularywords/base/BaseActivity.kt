package com.vynguyen.toeicvocabularywords.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vynguyen.toeicvocabularywords.R
import com.vynguyen.toeicvocabularywords.constant.Constant

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    fun onCreate() {
        setupToolbar()
        connectToolbarWithNaviDrawer()
        setupNaviDrawerClick()
        setUserName()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun connectToolbarWithNaviDrawer() {
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

    private fun setupNaviDrawerClick() {
        val navi = findViewById<NavigationView>(R.id.navigation)
        navi.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_header_navi_drawer_rename -> showRenameDialog()
        }
        return true
    }

    private fun showRenameDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_rename)

        val window = dialog.window ?: return

        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.attributes.gravity = Gravity.CENTER
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        val btnUpdate = dialog.findViewById<Button>(R.id.btn_update)
        val edtRename = dialog.findViewById<EditText>(R.id.edt_rename)
        edtRename.setText(getUserName())

        btnUpdate.setOnClickListener {
            val name = edtRename.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, R.string.pls_enter_text, Toast.LENGTH_SHORT).show()
            } else {
                saveUserName(name)
                Toast.makeText(this, R.string.rename_successfully, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun saveUserName(name: String) {
        val navigation = findViewById<NavigationView>(R.id.navigation)
        val headerLayout = navigation.getHeaderView(0)
        val tvUserName = headerLayout.findViewById<TextView>(R.id.tv_user_name)
        tvUserName.text = name

        val sharedPref =
            getSharedPreferences(Constant.APP_PREFERENCE_KEY, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(Constant.USER_NAME_KEY, name)
            apply()
        }
    }

    private fun getUserName(): String? {
        val defaultName = getString(R.string.default_user_name)
        val sharedPref = getSharedPreferences(Constant.APP_PREFERENCE_KEY, Context.MODE_PRIVATE)
            ?: return defaultName
        return sharedPref.getString(Constant.USER_NAME_KEY, defaultName)
    }

    private fun setUserName() {
        val navigation = findViewById<NavigationView>(R.id.navigation)
        val headerLayout = navigation.getHeaderView(0)
        val tvUserName = headerLayout.findViewById<TextView>(R.id.tv_user_name)
        tvUserName.text = getUserName()
    }

    override fun onBackPressed() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}