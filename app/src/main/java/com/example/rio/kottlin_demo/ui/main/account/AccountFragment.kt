package com.example.rio.kottlin_demo.ui.main.account


import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rio.kottlin_demo.databinding.FragmentAccountBinding
import com.example.rio.kottlin_demo.ui.base.BaseFragment

class AccountFragment :  BaseFragment<AccountViewModel>() {

    private lateinit var fragmentAccountBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        getViewReferences()
        return fragmentAccountBinding.getRoot()
    }

    override fun getViewReferences() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        fragmentAccountBinding.viewModel = viewModel
    }

    override fun initializeViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerEvents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeDataChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
