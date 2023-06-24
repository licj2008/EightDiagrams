package com.yunshuting.eightdiagrams.mv

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "yisqldb.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL("CREATE TABLE my_table (id INTEGER PRIMARY KEY, name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle any necessary upgrades to the schema
    }
}