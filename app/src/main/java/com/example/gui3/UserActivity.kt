package com.example.gui3

import android.content.ClipData
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView

class UserActivity() : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawer: DrawerLayout
    lateinit var fragmentContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val back = findViewById<ImageView>(R.id.back_icon)
        back.visibility = ImageView.INVISIBLE

        fragmentContainerView = findViewById(R.id.fragContainerUser)
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

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener (this)


    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END)
        }
        else if(supportFragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        }
        else{
            supportFragmentManager.popBackStack()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.nav_account -> {

            }

            R.id.nav_message -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragContainerUser, InboxFragment())
                transaction.addToBackStack(null) // Legger til i backstack får å kunne gå tilbake mellom fragments
                transaction.commit()
            }

            R.id.nav_arrangementer -> {

            }

            R.id.nav_nytt_arrangement -> {

                val intent = Intent(this, LagArrangementActivity::class.java)
                startActivity(intent)
            }

        }
        drawer.closeDrawer(GravityCompat.END)
        return true
    }
}