package com.yunshuting.eightdiagrams.mv

import android.content.Context

object SqlUtil {
    fun initDB() {

    }

    fun getDiagramById(){

    }

    fun getDiagramByUpdown(updown: String,context:Context){
        val dbHelper = MyDatabaseHelper(context)
        val db = dbHelper.readableDatabase

        val cursor = db.query("yidata", arrayOf("id", "name"), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            // Do something with the data
        }

        cursor.close()
        db.close()
    }


}