package com.example.helperstartup.View.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.helperstartup.View.catering.home.tab_fragment.*

class TabLayoutAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return SeninFragment()
            }
            1 -> {
                return SelasaFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                return RabuFragment()
            }
            3 -> {
                // val movieFragment = MovieFragment()
                return KamisFragment()
            }
            4 -> {
                // val movieFragment = MovieFragment()
                return JumatFragment()
            }
            5 -> {
                // val movieFragment = MovieFragment()
                return SabtuFragment()
            }
            6 -> {
                // val movieFragment = MovieFragment()
                return MingguFragment()
            }
            else -> return SeninFragment()
        }
    }
    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}