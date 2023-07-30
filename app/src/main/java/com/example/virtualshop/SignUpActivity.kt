package com.example.virtualshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val spDocument : AutoCompleteTextView = findViewById(R.id.spDocument)
        val documentList : List<String> = listOf("CE","DNI")
        spDocument.setAdapter(ArrayAdapter(this,R.layout.item_document,documentList))
     }
}