package com.example.rio.kottlin_demo.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.ArrayList


class ViewPagerAdapter : FragmentStatePagerAdapter {

    private var fragments: List<Fragment> = ArrayList()

    constructor(fm: FragmentManager) : super(fm) {
        // TODO Auto-generated constructor stub
    }

    constructor(fm: FragmentManager, fragments: List<Fragment>) : super(fm) {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return this.fragments[position]
    }

    override fun getCount(): Int {
      return  this.fragments.size
    }

    fun setFragments(fragments: List<Fragment>) {
        this.fragments = fragments
    }
}