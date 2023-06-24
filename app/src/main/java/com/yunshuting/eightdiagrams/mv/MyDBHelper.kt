package com.yunshuting.eightdiagrams.mv

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object MyDBHelper {
    val DB_NAME = "zhouyi.db";
    const val TABLE_NAME = "zhouyi_html"
//    lateinit var mContext:Context;
    /*选择题的集合*/
//    public List<Bean> mBeanLists = new ArrayList<Bean>();
//     MyDBHelper(mContext:Context) {
//        this.mContext = mContext;
//    }
    //把assets目录下的db文件复制到dbpath下
    fun getDatabase(mContext:Context) :SQLiteDatabase {
//        val dbPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases/" + DB_NAME;
        val dbPath = "/data/data/" + mContext.getPackageName() + "/mydb.db";
        if (!File(dbPath).exists()) {
            try {
                Log.d("licj","getDataByid 111 "+dbPath);

                val newFile = File(dbPath).createNewFile();
                Log.d("licj","getDataByid 222 ");
                try {
                    val out =  FileOutputStream(dbPath);
                    val ins = mContext.getAssets().open("yisqldb.db");
                    val buffer = ByteArray(1024)
                    var readBytes = 0;
                    while(true) {
                        readBytes = ins.read(buffer)
                        if (readBytes == -1)
                            break
                        out.write(buffer, 0, readBytes);
                    }
                    Log.d("licj","getDataByid 333 ");
                    ins.close();
                    out.close();
                    Log.d("licj","getDataByid 444 ");
                } catch (e : IOException) {
                    e.printStackTrace();
                }
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }

        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }


}