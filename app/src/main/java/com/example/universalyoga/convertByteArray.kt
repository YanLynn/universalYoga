package com.example.universalyoga

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream



fun byteArrayToBitmap(byteArray: ByteArray): Bitmap?{
    return if(byteArray.isNotEmpty()){
        BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }else{
        null
    }
}