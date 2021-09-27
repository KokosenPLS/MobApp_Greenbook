package com.example.gui3

import android.content.ClipData
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView

class UserActivity() : AppCompatActivity() {

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val back = findViewById<ImageView>(R.id.back_icon)
        back.visibility = ImageView.INVISIBLE

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawerLayout)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_open_drawer, R.string.navigation_close_drawer)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val menu_icon = findViewById<ImageView>(R.id.menu_icon)
        menu_icon.setOnClickListener{
            drawer.openDrawer(GravityCompat.END)
        }

    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END)
        }
        else
            super.onBackPressed()
    }
}