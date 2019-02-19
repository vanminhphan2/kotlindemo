package com.example.rio.kottlin_demo.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.ArrayList


class ViewPagerAdapter : FragmentStatePagerAdapter {

    private var fragments: ArrayList<Fragment> = ArrayList()

    constructor(fm: FragmentManager) : super(fm) {

    }

    constructor(fm: FragmentManager, fragments: ArrayList<Fragment>) : super(fm) {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return this.fragments[position]
    }

    override fun getCount(): Int {
      return  this.fragments.size
    }

    fun setFragments(fragments: ArrayList<Fragment>) {
        this.fragments = fragments
    }
}