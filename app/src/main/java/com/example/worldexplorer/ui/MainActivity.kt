package com.example.worldexplorer.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.worldexplorer.R
import com.example.worldexplorer.util.KEY_SEARCH
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentSearchQuery: String? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this).load(R.drawable.background_image).into(background_view)

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        setupActionBarWithNavController(navController, null)

        navController.addOnDestinationChangedListener { controller: NavController,
                                                        destination: NavDestination,
                                                        args: Bundle? ->
            run {
                if (destination == controller.graph.findNode(R.id.searchListFragment))
                    toolbar.title =
                        resources.getString(R.string.search_fragment_label) + args?.getString(
                            KEY_SEARCH
                        )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = resources.getString(R.string.search_view_hint_message)

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    currentSearchQuery = query
                    val bundle = Bundle()
                    bundle.putString(KEY_SEARCH, query)
                    navController.navigate(R.id.searchListFragment, bundle)
                    toolbar.title = resources.getString(R.string.search_fragment_label) + query
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (navController.currentDestination == navController.graph.findNode(R.id.searchListFragment) && newText.isEmpty())
                    navController.navigate(R.id.worldListFragment)
                return false
            }
        })
        return true
    }
}