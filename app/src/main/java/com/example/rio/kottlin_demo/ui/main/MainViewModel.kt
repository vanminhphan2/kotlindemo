package com.example.rio.kottlin_demo.ui.main

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewData
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<MainViewData>() {

}