package com.example.apppayments.Helpers

import android.app.Application
import android.util.Base64
import android.util.Base64.encodeToString

class DataGlobal: Application() {

        var commerceCode:String="000123"
        var terminalCode :String="000ABC"
        var card:String="1234567890123456"
var keyApi:String= (Base64.encodeToString((commerceCode+terminalCode).toByteArray(), Base64.DEFAULT)).replace("\n","")

}


