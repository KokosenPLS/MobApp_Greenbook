package com.example.greenbook.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.greenbook.Database
import com.example.greenbook.NavGraphDirections
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Profil
import com.example.greenbook.fragments.LoggInnFragmentDirections
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var database: Database
    private lateinit var auth: FirebaseAuth
    //nav header
    private lateinit var navnNavHeader:TextView
    private lateinit var mailNavHeader:TextView
    private lateinit var bildeNavHeader:ImageView
    private lateinit var brukerNavn:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        database = Database()
        getProfil()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.loggInnFragment, R.id.feedFragment),
            drawerLayout)

        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(navController, appBarConfiguration)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_nytt_arrangement -> {
                    val action = NavGraphDirections.actionGlobalLagArrangementFragment()
                    navController.navigate(action)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_arrangementer -> {
                    val action = NavGraphDirections.actionGlobalDineArrangementerFragment()
                    navController.navigate(action)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_inbox -> {
                    val action =  NavGraphDirections.actionGlobalInboxFragment()
                    navController.navigate(action)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_account -> {
                    val action =  NavGraphDirections.actionGlobalProfilFragment(auth.uid.toString(), brukerNavn)
                    navController.navigate(action)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    false
                }
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.menu_logout){
            auth.signOut()
            reload()
            true
        }
        else
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun reload(){
        finish()
        startActivity(Intent(this, LaunchActivity::class.java))
    }

    private fun getProfil(){
        val profilListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profil = snapshot.getValue<Profil>()!!
                update(profil!!)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.database.child("bruker").child(auth.uid.toString()).addValueEventListener(profilListener)
    }

    @SuppressLint("SetTextI18n")
    private fun update(profil: Profil){
        navnNavHeader = findViewById(R.id.nav_header_navn)
        mailNavHeader = findViewById(R.id.nav_header_email)
        bildeNavHeader = findViewById(R.id.nav_header_bilde)

        brukerNavn = profil.fornavn + " " + profil.etternavn
        navnNavHeader.text = profil.fornavn + " " + profil.etternavn
        mailNavHeader.text = profil.email
        if(profil.imgUrl !=null)
            Picasso.get().load(profil.imgUrl).into(bildeNavHeader)
    }


}