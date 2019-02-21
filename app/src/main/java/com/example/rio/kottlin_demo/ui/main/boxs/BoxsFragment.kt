package com.example.rio.kottlin_demo.ui.main.boxs


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rio.kottlin_demo.data.model.Box

import com.example.rio.kottlin_demo.databinding.FragmentBoxsBinding
import com.example.rio.kottlin_demo.ui.adapter.ListBoxAdapter
import com.example.rio.kottlin_demo.ui.base.BaseFragment

class BoxsFragment : BaseFragment<BoxsViewModel>() {

    private lateinit var fragmentBoxsBinding:FragmentBoxsBinding
    private lateinit var listBoxAdapter: ListBoxAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("Rio ","onCreateView  BoxsFragment")
        fragmentBoxsBinding = FragmentBoxsBinding.inflate(inflater, container, false)
        getViewReferences()
        initializeViews()
        registerEvents()
        observeDataChange()
        return fragmentBoxsBinding.getRoot()
    }

    override fun getViewReferences() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BoxsViewModel::class.java)
        fragmentBoxsBinding.viewModel = viewModel
    }

    override fun initializeViews() {

        val layoutManager= LinearLayoutManager(context)
        listBoxAdapter= ListBoxAdapter(viewModel.boxsViewData.listBox,context)
        fragmentBoxsBinding.recyclerBoxs.layoutManager=layoutManager
        fragmentBoxsBinding.recyclerBoxs.adapter=listBoxAdapter
    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            fragmentBoxsBinding.viewModel = viewModel
        })

        viewModel.getUpdateListBoxEvent().observe(this, Observer {
            listBoxAdapter.setListData(viewModel.boxsViewData.listBox)
        })

        viewModel.getListBoxSuccessEvent().observe(this, Observer {
            listBoxAdapter.setListData(viewModel.boxsViewData.listBox)
        })
    }

    override fun observeDataChange() {

        viewModel.getUserLogin()
//        viewModel.getListBox()
    }
}
