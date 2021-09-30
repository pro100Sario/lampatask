package com.example.lampatask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.lampatask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tabLayout.setupWithViewPager(binding.viewpager)

        val tabFragments = arrayListOf<TabFragment>(
            TabFragment.newInstance(ContentType.STRORIES),
            TabFragment.newInstance(ContentType.VIDEO),
            TabFragment.newInstance(ContentType.FAVOURITES)
        )


        val pagerAdapter = PagerAdapter(supportFragmentManager, tabFragments = tabFragments)
        binding.viewpager.adapter = pagerAdapter

        tabFragments.forEachIndexed { index, fragment ->
                binding.tabLayout.getTabAt(index)?.text = fragment.getTypeName()
        }


        viewModel.loadData()
    }


    private inner class PagerAdapter(fm: FragmentManager, val tabFragments: ArrayList<TabFragment>) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = tabFragments.size

        override fun getItem(position: Int): Fragment = tabFragments[position]
    }




}