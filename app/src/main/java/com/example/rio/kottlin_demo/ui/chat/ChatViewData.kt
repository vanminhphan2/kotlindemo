package com.example.rio.kottlin_demo.ui.chat

import com.example.rio.kottlin_demo.data.model.Box
import com.example.rio.kottlin_demo.data.model.Message
import com.example.rio.kottlin_demo.ui.base.BaseViewData

class ChatViewData:BaseViewData(){

    var listChat= arrayListOf<Message>()
    var mainBox=Box()
    var idUserReceive=""
    var idUserLogin=""
    var contentMess=""
}