package com.example.virtualshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virtualshop.databinding.ActivityMainBinding
import com.example.virtualshop.localstorage.GlobalAppication

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(GlobalAppication.prefs.getJWT().isNotEmpty()){
            startActivity(Intent(this, ContentFrameActivity::class.java))
        }
        //GlobalAppication.prefs.clearStorage()

        binding.btnAccessA1.setOnClickListener {
            val intent = Intent(this,  AccessAppActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this,  SignUpActivity::class.java)
            startActivity(intent)
        }

    }


}