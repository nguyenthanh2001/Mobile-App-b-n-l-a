package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS user \n" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                " ten TEXT, \n" +
                " giamua TEXT,\n" +
                " sobao text,\n" +
                " khoiluong text,\n" +
                " thanhtien text,\n" +
                " tiencoc text,\n" +
                " datra text,\n" +
                " conlai text,\n" +
                " tapchat text,\n" +
                " klconlai text,\n" +
                " sodienthoai text,\n" +
                " user_id INTEGER, \n" +
                " FOREIGN KEY (user_id) REFERENCES user(_id) ON DELETE CASCADE\n" +
                ")")
        db?.execSQL("CREATE TABLE IF NOT EXISTS nhom \n" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                " tennhom TEXT,\n" +
                " ngaymua TEXT,\n" +
                " sdt TEXT,\n" +
                " tongkl TEXT,\n" +
                " tongbao TEXT\n" +
                ")")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {


    }


}