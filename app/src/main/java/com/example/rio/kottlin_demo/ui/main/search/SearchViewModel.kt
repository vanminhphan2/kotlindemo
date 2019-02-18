package com.example.rio.kottlin_demo.ui.main.search

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(private var appDataManager: AppDataManager) :
    BaseViewModel<SearchViewData>() {

}