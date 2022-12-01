package com.example.lab5

import android.Manifest.permission.SEND_SMS
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lab5.db.DbWorker


class SmsActivity : AppCompatActivity() {
    private lateinit var listView : ListView
    private val CONTACT_PERMISSION_CODE = 1;
    private val CONTACT_PICK_CODE = 2
    private val requestSendSms  = 2;
    private val dbWorker : DbWorker = DbWorker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        val editText = findViewById<View>(R.id.editTextTextMultiLine) as EditText
        listView = findViewById<View>(R.id.templateListView) as ListView
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            editText.setText(listView.getItemAtPosition(position).toString(), TextView.BufferType.EDITABLE)
        }
        loadTemplates()
    }

    fun sendSmsBtnClick(view: View) {
        if(ActivityCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(SEND_SMS),requestSendSms)
        else sendSms();

    }

    private fun sendSms(){
        val editText = findViewById<View>(R.id.editTextTextMultiLine) as EditText
        val phone = findViewById<View>(R.id.editTextPhone) as EditText
        if(editText.text.toString() != "" && phone.text.toString()!= "Телефон") {
            SmsManager.getDefault()
                .sendTextMessage(phone.text.toString(), null, editText.text.toString(), null, null)

            dbWorker.add(phone.text.toString(), editText.text.toString())
            Toast.makeText(this, "Сообщение отправлено", Toast.LENGTH_SHORT).show()
            editText.setText("")
        }else Toast.makeText(this, "Введите сообщение и номер телефона", Toast.LENGTH_SHORT).show()
    }
    private fun loadTemplates()
    {
        val templates = dbWorker.getTemplates()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, templates.toArray()
        )
        for(msg in templates)
        {
            listView.adapter = adapter
        }
    }

    private fun checkContactPermission(): Boolean{
        return  ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission(){
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(this, permission, CONTACT_PERMISSION_CODE)
    }

    private fun pickContact(){
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }

    fun getContact(view: android.view.View) {
        if (checkContactPermission()){
            pickContact()
        }
        else{
            requestContactPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            var phone : String = ""
            if (requestCode == CONTACT_PICK_CODE) {

                val cursor1: Cursor
                val cursor2: Cursor?

                //get data from intent
                val uri = data!!.data
                cursor1 = contentResolver.query(uri!!, null, null, null, null)!!
                if (cursor1.moveToFirst()) {
                    //get contact details
                    val contactId =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID).toInt())
                    val contactName =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).toInt())
                    val contactThumbnail =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI).toInt())
                    val idResults =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt())
                    val idResultHold = idResults.toInt()
                    phone = "ID: $contactId"
                    phone ="\nName: $contactName"
                    if (idResultHold == 1) {
                        cursor2 = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null
                        )
                        while (cursor2!!.moveToNext()) {
                            val contactNumber =
                                cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER).toInt())
                            phone = contactNumber

                        }
                        cursor2.close()
                    }
                    cursor1.close()
                }

            }
            val editText = findViewById<EditText>(R.id.editTextPhone) as EditText
            editText.setText(phone, TextView.BufferType.EDITABLE);
        }
    }


    fun addTemplateBtnClick(view: android.view.View) {
        val editText = findViewById<View>(R.id.editTextTextMultiLine) as EditText
        if(editText.text.toString() != "") {
            dbWorker.addTemplate(editText.text.toString())
            loadTemplates()
        }
    }
}