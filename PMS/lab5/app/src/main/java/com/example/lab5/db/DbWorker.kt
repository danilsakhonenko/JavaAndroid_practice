package com.example.lab5.db

import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


class DbWorker (context: Context?){
    val dbHelper : DBHelper = DBHelper(context)

    fun add(phone : String, msg : String){
        val contentValues = ContentValues()
        contentValues.put(dbHelper.PHONE, phone)
        contentValues.put(dbHelper.MESSAGE, msg)
        val db = dbHelper.writableDatabase
        db.insert(dbHelper.MSG_TABLE, null, contentValues)
        db.close()
    }

    fun getMessages(count: Int) : ArrayList<String> {
        val msgList = arrayListOf<String>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(dbHelper.MSG_TABLE, null, null, null, null, null, dbHelper.ID + " desc");

        while (cursor.moveToNext()) {
            val phone = cursor.getString(1)
            val msg = cursor.getString(2)

            if(msgList.size >= count)
                break;

            msgList.add("$phone : $msg")
        }
        db.close()
        return msgList
    }

    fun addTemplate(template: String){
        val contentValues = ContentValues()
        contentValues.put(dbHelper.TEMPLATE, template)
        val db = dbHelper.writableDatabase
        db.insert(dbHelper.TEMPLATE_TABLE, null, contentValues)
        db.close()
    }

    fun getTemplates() : ArrayList<String>{
        val msgList = arrayListOf<String>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(dbHelper.TEMPLATE_TABLE, null, null, null, null, null, dbHelper.ID + " desc");

        while (cursor.moveToNext()) {
            val template = cursor.getString(1)

            msgList.add(template)
        }
        db.close()
        return msgList
    }
}