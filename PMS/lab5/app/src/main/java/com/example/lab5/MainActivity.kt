package com.example.lab5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.lab5.db.DbWorker
import android.widget.ArrayAdapter
import android.provider.ContactsContract


class MainActivity : AppCompatActivity() {
    val dbWorker = DbWorker(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMessages()
    }

    override fun onRestart() {
        loadMessages()
        super.onRestart()
    }
    private fun loadMessages()
    {
        val messages = dbWorker.getMessages(10)
        val listView = findViewById<ListView>(R.id.msgListView)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, messages.toArray()
        )
        for(msg in messages)
        {
            listView.adapter = adapter
        }
    }

    fun SmsBtnClick(view: android.view.View) {
        val intent = Intent(this,SmsActivity::class.java)
        startActivity(intent)
    }

    fun addContactBtnClick(view: android.view.View) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        startActivity(intent)
    }

    fun EmailBtnClick(view: android.view.View) {
        val intent = Intent(this,EmailActivity::class.java)
        startActivity(intent)
    }
}