package com.example.rio.kottlin_demo.ui.main.boxs


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
import java.util.*

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
        return fragmentBoxsBinding.getRoot()
    }

    override fun getViewReferences() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BoxsViewModel::class.java)
        fragmentBoxsBinding.viewModel = viewModel
    }

    override fun initializeViews() {

        val layoutManager= LinearLayoutManager(context)
        val box=Box("123","hop box so 1","111","112","ahihaihi")
        val array :ArrayList<Box> = arrayListOf(box,box,box,box,box,box,box,box,box,box,box,box)
        listBoxAdapter= ListBoxAdapter(array,context)
        fragmentBoxsBinding.recyclerBoxs.layoutManager=layoutManager
        fragmentBoxsBinding.recyclerBoxs.adapter=listBoxAdapter
    }

    override fun registerEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeDataChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
