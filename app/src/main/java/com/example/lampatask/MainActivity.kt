package com.example.lampatask

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.lifecycleScope
import com.example.lampatask.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var menuItem: MenuItem? = null

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.tabLayout.setupWithViewPager(binding.viewpager)

        val tabFragments = arrayListOf(
            TabFragment.newInstance(ContentType.STRORIES),
            TabFragment.newInstance(ContentType.VIDEO),
            TabFragment.newInstance(ContentType.FAVOURITES)
        )


        val pagerAdapter = PagerAdapter(supportFragmentManager, tabFragments = tabFragments)
        binding.viewpager.adapter = pagerAdapter

        tabFragments.forEachIndexed { index, fragment ->
            binding.tabLayout.getTabAt(index)?.text = fragment.getTypeName()
        }

        binding.swiperefresh.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(500)
                binding.swiperefresh.isRefreshing = false
                loadData()
            }
        }


        loadData()
    }

    private fun loadData() {
        closeSearch()
        viewModel.loadData().observe(this, {
            when (it) {
                is Outcome.Failure -> {
                    hideProgress()
                    showError {
                        loadData()
                    }
                }
                is Outcome.Progress -> {
                    showProgress()
                }
                is Outcome.Success -> {
                    hideProgress()
                }
            }
        })
    }


    private inner class PagerAdapter(
        fm: FragmentManager,
        val tabFragments: ArrayList<TabFragment>
    ) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = tabFragments.size

        override fun getItem(position: Int): Fragment = tabFragments[position]
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.search)

        this.menuItem = menuItem

        val searchView = LayoutInflater.from(this).inflate(R.layout.custom_search_view, null, false) as SearchView

        menuItem.actionView = searchView

        searchView.queryHint = "search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.query(it) }
                return true
            }
        })

        val searchPlate = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        val closeButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        searchPlate.setTextColor(ContextCompat.getColor(this, R.color.white))
        searchPlate.setHintTextColor(ContextCompat.getColor(this, R.color.color_divider))
        closeButton.setColorFilter(ContextCompat.getColor(this, R.color.color_divider))



        return super.onCreateOptionsMenu(menu)
    }


    override fun onBackPressed() {
        if (menuItem?.isActionViewExpanded == true) {
            closeSearch()
        } else {
            super.onBackPressed()
        }
    }


    private fun closeSearch() {
            menuItem?.collapseActionView()

    }

}