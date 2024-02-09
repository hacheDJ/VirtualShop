package com.example.virtualshop.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.virtualshop.ContentFrameActivity
import com.example.virtualshop.MainActivity
import com.example.virtualshop.R
import com.example.virtualshop.databinding.FragmentProfileBinding
import com.example.virtualshop.localstorage.GlobalAppication
import com.example.virtualshop.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        lifecycleScope.launch{
            val response = withContext(Dispatchers.IO){
                Api.build().detailOfUser()
            }

            if(response.isSuccessful){
                val detailOfUserRes = response.body()

                if(detailOfUserRes!!.err){
                    println(detailOfUserRes.msg)
                }else{
                    binding.tvNameUser.text = detailOfUserRes.data.nameUser
                    binding.tvAddressUser.text = detailOfUserRes.data.address
                }
            }

        }

        binding.tvOptionLogOut.setOnClickListener {
            GlobalAppication.prefs.clearStorage()

            startActivity(Intent(this.context, MainActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if(GlobalAppication.prefs.getJWT().isEmpty()){
            startActivity(Intent(this.context, MainActivity::class.java))
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}