package com.example.rio.kottlin_demo.ui.main.search


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.databinding.FragmentSearchBinding
import com.example.rio.kottlin_demo.ui.adapter.ListSearchResultAdapter
import com.example.rio.kottlin_demo.ui.base.BaseFragment
import com.example.rio.kottlin_demo.ui.chat.ChatActivity
import com.example.rio.kottlin_demo.utils.AppConstants
import com.example.rio.kottlin_demo.utils.inf.MyCallBack

class SearchFragment : BaseFragment<SearchViewModel>() {

    private lateinit var fragmentSearchBinding:FragmentSearchBinding
    private lateinit var listSearchResultAdapter: ListSearchResultAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.e("Rio ","onCreateView  SearchFragment")
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        getViewReferences()
        initializeViews()
        registerEvents()
        observeDataChange()
        return fragmentSearchBinding.getRoot()
    }

    override fun getViewReferences() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        fragmentSearchBinding.viewModel = viewModel
    }

    override fun initializeViews() {

        val layoutManager= LinearLayoutManager(context)
        listSearchResultAdapter= ListSearchResultAdapter(viewModel.searchViewData.listUser,context)
        fragmentSearchBinding.recyclerListUser.layoutManager=layoutManager
        fragmentSearchBinding.recyclerListUser.adapter=listSearchResultAdapter
        listSearchResultAdapter.setMyCallback(MyCallBack { t->

            val user:User= t as User
            if(viewModel.checkIsLoginId(user.id)){
                val starter = Intent(getBaseActivity(), ChatActivity::class.java)
                starter.putExtra(AppConstants.USER_CHOOSE,user)
                startActivity(starter)
            }
            else{
                Toast.makeText(context,"It's you!",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun registerEvents() {

        viewModel.getViewData().observe(this, Observer {
            fragmentSearchBinding.viewModel = viewModel
        })

        viewModel.getUpdateListUserEvent().observe(this, Observer {
            listSearchResultAdapter.setListData(viewModel.searchViewData.listUser)
        })

    }

    override fun observeDataChange() {
//       viewModel.getListUser()
        viewModel.getListUserFromFireStore()
    }

}
