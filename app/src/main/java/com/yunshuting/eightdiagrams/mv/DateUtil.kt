package com.yunshuting.eightdiagrams.mv

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    fun getCurTime():String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formattedDateTime = currentDateTime.format(formatter)
            return formattedDateTime
        } else {
            val calendar = Calendar.getInstance()

            // 获取当前时间
            val currentDateTime = calendar.time

            // 创建日期时间格式化器
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

            // 格式化当前时间
            val formattedDateTime = formatter.format(currentDateTime)

            return formattedDateTime
        }
    }
}