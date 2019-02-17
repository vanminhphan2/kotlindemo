package com.example.rio.kottlin_demo.ui.register

import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewData

class RegisterViewData : BaseViewData(){

    var isShowLoading:Boolean=false
    var isEnabledEtPhone:Boolean=true
    var isVisibilityBtnGetCode:Boolean=true
    var isVisibilityEtCode:Boolean=false
    var isVisibilityBtnCheckCode:Boolean=false
    var isVisibilityEtNameCode:Boolean=false
    var isVisibilityEtPassCode:Boolean=false
    var isVisibilityBtnRegister:Boolean=false
    var message:String=""
    var idUser:String=""
    var phone:String=""
    var name:String=""
    var pass:String=""
    var code:String="123456"
    var verificationId:String=""
    var forceResendingToken:String=""
}