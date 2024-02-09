package com.example.virtualshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.virtualshop.databinding.ActivitySignUpBinding
import com.example.virtualshop.dto.SignupReq
import com.example.virtualshop.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spDocument : AutoCompleteTextView = findViewById(R.id.spDocument)
        val documentList : List<String> = listOf("DNI","CE","PA")
        spDocument.setAdapter(ArrayAdapter(this,R.layout.item_document,documentList))

        binding.btnSignUp.setOnClickListener {
            if(binding.edtInsertName.text.toString().isEmpty()) return@setOnClickListener
            if(binding.edtLastname.text.toString().isEmpty()) return@setOnClickListener
            if(binding.edtDocumentNumber.text.toString().isEmpty()) return@setOnClickListener
            if(binding.edtInsertYourDirection.text.toString().isEmpty()) return@setOnClickListener
            if(binding.edtInsertYourEmail.text.toString().isEmpty()) return@setOnClickListener
            if(binding.edtPassword.text.toString().isEmpty()) return@setOnClickListener
            if(binding.edtConfirmPassword.text.toString().isEmpty()) return@setOnClickListener

            if(!binding.cbxConfirmTerms.isChecked) return@setOnClickListener

            val gender = if(binding.rbFemale.isChecked) "female" else "male"
            val typeDoc = binding.spDocument.text.toString()

            val signupReq = SignupReq(
                nameUser = binding.edtInsertName.text.toString(),
                lastNameUser = binding.edtLastname.text.toString(),
                gender = gender,
                documentType = typeDoc,
                documentNumber = binding.edtDocumentNumber.text.toString(),
                address = binding.edtInsertYourDirection.text.toString(),
                email = binding.edtInsertYourEmail.text.toString(),
                passwordUser = binding.edtPassword.text.toString(),
                confPass = binding.edtConfirmPassword.text.toString()
            )

            lifecycleScope.launch{
                try {
                    binding.pbSignup.visibility = View.VISIBLE

                    val response = withContext(Dispatchers.IO) {
                        Api.build().signup(signupReq)
                    }

                    if (response.isSuccessful) {
                        val signupRes = response.body()

                        if (!signupRes!!.err) {
                            Toast.makeText(applicationContext, signupRes.msg, Toast.LENGTH_LONG)
                                .show()
                            onBackPressed()
                        } else {
                            Toast.makeText(this@SignUpActivity, signupRes.msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }catch(ex: Exception){
                    println(ex.message)
                }finally {
                    binding.pbSignup.visibility = View.GONE
                }
            }


        }

     }


}