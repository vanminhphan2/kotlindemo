package com.example.rio.kottlin_demo.ui.main.boxs

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import javax.inject.Inject

class BoxsViewModel @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<BoxsViewData>()