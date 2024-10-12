package com.example.universalyoga

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.universalyoga.Activity.Class.AddClassActivity
import com.example.universalyoga.Adapter.CourseAdapter
import com.example.universalyoga.DBHelper.CourseDBHelper
import com.example.universalyoga.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var courseDBHelper: CourseDBHelper
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        courseDBHelper = CourseDBHelper(this)
        enableEdgeToEdge()
        setContentView(binding.root)
        //initialize toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Yoga"

        //initialize drawer layout and nav view
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        //set up drawer toggle
        drawerToggle = ActionBarDrawerToggle(
            this,drawerLayout,binding.toolbar,
            R.string.drawer_open,R.string.drawer_close
        )
        //add drawer toggle to drawer layout
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()




        //courseDBHelper.deleteAll()
        courseAdapter = CourseAdapter(courseDBHelper.getAllCourse(),courseDBHelper)


        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = courseAdapter



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Handle navigation item click
        navView.setNavigationItemSelectedListener { menuItem ->
             when(menuItem.itemId) {
                R.id.nav_course -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                 R.id.nav_class -> {
                     val intent = Intent(this, AddClassActivity::class.java)
                     startActivity(intent)
                 }



            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


}

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Keyword Search"

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    courseAdapter.filter(newText)
                }
                return true
            }

        })
        return true
    }
    //click menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.uploadDB -> {


                true
            }
            R.id.syncDB -> {

                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }


} // end