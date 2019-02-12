package com.example.rio.kottlin_demo.ui.login

import com.example.rio.kottlin_demo.data.AppDataManager
import com.example.rio.kottlin_demo.ui.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(private var appDataManager: AppDataManager):BaseViewModel<LoginViewData>(){}