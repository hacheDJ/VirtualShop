package com.example.virtualshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.virtualshop.databinding.ActivityAccessAppBinding
import com.example.virtualshop.dto.SigninReq
import com.example.virtualshop.dto.SigninRes
import com.example.virtualshop.localstorage.GlobalAppication
import com.example.virtualshop.localstorage.Prefs
import com.example.virtualshop.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccessAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccessAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccessAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAccessA2.setOnClickListener {
            val signinReq = SigninReq(
                email = binding.edtUserInput.text.toString(),
                passwordUser = binding.edtPassword.text.toString()
            )

            lifecycleScope.launch {
                try {
                    binding.pbAccess.visibility = View.VISIBLE

                    val response = withContext(Dispatchers.IO){
                        Api.build().signin(signinReq)
                    }

                    if(response.isSuccessful){
                        val signinRes = response.body()

                        if(!signinRes!!.err){
                            GlobalAppication.prefs.setJWT(signinRes.token)

                            val intent = Intent(this@AccessAppActivity, ContentFrameActivity::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this@AccessAppActivity, signinRes.msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                }catch (ex: Exception){

                }finally {
                    binding.pbAccess.visibility = View.GONE
                }

            }

        }

    }


    override fun onStart() {
        super.onStart()

        if(GlobalAppication.prefs.getJWT().isNotEmpty()){
            startActivity(Intent(this, ContentFrameActivity::class.java))
        }
    }

}