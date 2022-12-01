package com.example.lab5

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class EmailActivity : AppCompatActivity() {

    private lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            uri = data?.data!!;
            var filename = findViewById<View>(R.id.files_text) as TextView
            filename.text=uri.path.toString();
        }catch (ex: Exception){}
    }
    fun sendEmailBtnClick(view: android.view.View) {
        try {
            val editText = findViewById<View>(R.id.editTextTextMultiLine1) as EditText
            val emailIntent = Intent(android.content.Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, editText.text.toString());
            emailIntent.type = "text/plain";
            startActivity(Intent.createChooser(emailIntent, "Отправка письма..."));
        }catch (ex: Exception){
            Toast.makeText(this, "Картинка не выбрана", Toast.LENGTH_SHORT).show()}
    }

    fun pickPictureBtnClick(view: android.view.View) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 0)
    }
}