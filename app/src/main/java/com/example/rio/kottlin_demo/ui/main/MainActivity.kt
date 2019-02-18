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
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject
import android.databinding.adapters.CompoundButtonBindingAdapter.setChecked
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem


class MainActivity :BaseActivity<MainViewModel>() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var boxsFragment:BoxsFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var prevMenuItem: MenuItem

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_box -> {
                activityMainBinding.viewpager.setCurrentItem(0);
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                activityMainBinding.viewpager.setCurrentItem(1);
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
        boxsFragment= BoxsFragment()
        accountFragment=AccountFragment()
        val array = arrayOf(boxsFragment,accountFragment)
        val list = Arrays.asList(*array)
        val adapter = ViewPagerAdapter(supportFragmentManager,list)
        activityMainBinding.viewpager.setAdapter(adapter)
        prevMenuItem = activityMainBinding.navigation.getMenu().getItem(0)
        activityMainBinding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
//                Log.e("Rio","onPageSelected")
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false)
                else
                    activityMainBinding.navigation.getMenu().getItem(0).setChecked(false)

                activityMainBinding.navigation.getMenu().getItem(position).setChecked(true)
                prevMenuItem = activityMainBinding.navigation.getMenu().getItem(position)
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

    override fun observeDataChange() {
    }

}
