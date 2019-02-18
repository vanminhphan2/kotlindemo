package com.example.rio.kottlin_demo.ui.main.search


import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.databinding.FragmentSearchBinding
import com.example.rio.kottlin_demo.ui.adapter.ListSearchResultAdapter
import com.example.rio.kottlin_demo.ui.base.BaseFragment

class SearchFragment : BaseFragment<SearchViewModel>() {

    private lateinit var fragmentSearchBinding:FragmentSearchBinding
    private lateinit var listSearchResultAdapter: ListSearchResultAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        getViewReferences()
        initializeViews()
        return fragmentSearchBinding.getRoot()
    }

    override fun getViewReferences() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        fragmentSearchBinding.viewModel = viewModel
    }

    override fun initializeViews() {

        val layoutManager= LinearLayoutManager(context)
        val user= User("123","rio pro kute so handsome ahihi","111","112")
        val array :ArrayList<User> = arrayListOf(user,user,user,user,user,user,user,user,user,user,user,user,user,user)
        listSearchResultAdapter= ListSearchResultAdapter(array,context)
        fragmentSearchBinding.recyclerListUser.layoutManager=layoutManager
        fragmentSearchBinding.recyclerListUser.adapter=listSearchResultAdapter
    }

    override fun registerEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeDataChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
