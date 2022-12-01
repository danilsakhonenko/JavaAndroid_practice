package com.example.lab5.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + TABLE_MESSAGES + "(" + KEY_ID
                    + " integer primary key," + KEY_PHONE + " text," + KEY_MESSAGE + " text" + ")"
        )
        db.execSQL(
            "create table " + TABLE_TEMPLATES + "(" + KEY_ID
                    + " integer primary key," + KEY_TEMPLATE + " text)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists " + TABLE_MESSAGES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "5lab"
        const val TABLE_MESSAGES = "messages"
        const val KEY_ID = "_id"
        const val TABLE_TEMPLATES = "templates"
        const val KEY_TEMPLATE = "template"
        const val KEY_PHONE = "phone"
        const val KEY_MESSAGE = "msg"
    }
    val ID = KEY_ID
    val PHONE = KEY_PHONE
    val MESSAGE = KEY_MESSAGE
    val MSG_TABLE = TABLE_MESSAGES
    val TEMPLATE_TABLE = TABLE_TEMPLATES
    val TEMPLATE = KEY_TEMPLATE
}