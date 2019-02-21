package com.example.rio.kottlin_demo.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.rio.kottlin_demo.R
import com.example.rio.kottlin_demo.databinding.ActivityMainBinding
import com.example.rio.kottlin_demo.ui.adapter.ViewPagerAdapter
import com.example.rio.kottlin_demo.ui.base.BaseActivity
import com.example.rio.kottlin_demo.ui.login.LoginActivity
import com.example.rio.kottlin_demo.ui.main.account.AccountFragment
import com.example.rio.kottlin_demo.ui.main.boxs.BoxsFragment
import java.util.*
import android.support.v4.view.ViewPager
import android.util.Log
import com.example.rio.kottlin_demo.ui.main.search.SearchFragment


class MainActivity :BaseActivity<MainViewModel>() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private  var boxsFragment:BoxsFragment
    private  var accountFragment: AccountFragment
    private var searchFragment: SearchFragment
    private var listFragment : ArrayList<Fragment>
    private var viewPagerAdapter:ViewPagerAdapter

    init {
        boxsFragment= BoxsFragment()
        searchFragment= SearchFragment()
        accountFragment=AccountFragment()
        listFragment=ArrayList()
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, listFragment)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> {
                activityMainBinding.viewpager.setCurrentItem(0);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_box -> {
                activityMainBinding.viewpager.setCurrentItem(1);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                activityMainBinding.viewpager.setCurrentItem(2);
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewReferences()
        registerEvents()
        initializeViews()
    }

    override fun getViewReferences() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        activityMainBinding.viewModel = viewModel
        activityMainBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun initializeViews() {

        listFragment.add(searchFragment)
        listFragment.add(boxsFragment)
        listFragment.add(accountFragment)
        activityMainBinding.viewpager.setAdapter(viewPagerAdapter)
        activityMainBinding.viewpager.offscreenPageLimit=2
        activityMainBinding.viewpager.setCurrentItem(1);
        activityMainBinding.navigation.getMenu().getItem(1).setChecked(true)
        activityMainBinding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

               when(position) {

                   0 -> {
                       activityMainBinding.navigation.getMenu().getItem(1).setChecked(false)
                       activityMainBinding.navigation.getMenu().getItem(2).setChecked(false)
                   }
                   1 -> {
                       activityMainBinding.navigation.getMenu().getItem(0).setChecked(false)
                       activityMainBinding.navigation.getMenu().getItem(2).setChecked(false)
                   }
                   else -> {
                       activityMainBinding.navigation.getMenu().getItem(0).setChecked(false)
                       activityMainBinding.navigation.getMenu().getItem(1).setChecked(false)
                   }
               }
//                Log.e("Rio ","listFragment size " + listFragment.size)
                activityMainBinding.navigation.getMenu().getItem(position).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun registerEvents() {
        viewModel.getToLoginEvent().observe(this, Observer{
            val starter = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(starter)
            finish()
        })
    }

    override fun initData() {
    }

}
