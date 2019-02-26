package com.example.rio.kottlin_demo.ui.chat

import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.data.model.User
import com.example.rio.kottlin_demo.ui.base.BaseViewData

class ChatViewData:BaseViewData(){

    var mainBox=Box()
    var boxReceive=Box()
    var listMess= arrayListOf<Message>()
    var userReceive= User()
    var contentMess=""
    var messSent=Message()
    var messReceive=Message()
    var isHadBox=false
}