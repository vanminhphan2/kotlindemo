package com.example.rio.kottlin_demo.ui.main.account

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import javax.inject.Inject

class AccountViewModel  @Inject constructor(private var appDataManager: AppDataManager) : BaseViewModel<AccountViewData>()