package com.example.virtualshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.example.virtualshop.R
import com.example.virtualshop.databinding.FragmentDeliveryInformationBinding
import com.example.virtualshop.dto.DetailOfUserRes
import com.example.virtualshop.model.ShoppingCart
import com.example.virtualshop.networking.Api
import com.example.virtualshop.util.DataPickerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DeliveryInformationFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryInformationBinding

    private var arLstSC: ArrayList<ShoppingCart>? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arLstSC = it.getParcelableArrayList("KEY_ARRAYLIST_SC")
            //param2 = it.getString(ARG_PARAM2)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryInformationBinding.inflate(inflater, container, false)

        var currentAddressUser = ""

        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO){
                Api.build().detailOfUser()
            }

            if(response.isSuccessful){
                val detailOfUserRes = response.body()

                if(detailOfUserRes!!.err){

                }else{
                    currentAddressUser = detailOfUserRes.data.address
                }

            }

        }



        binding.rbOne.setOnClickListener {
            binding.etOtherAddress.setText("")
            binding.etOtherAddress.isEnabled = false
        }

        binding.rbTwo.setOnClickListener {
            binding.etOtherAddress.isEnabled = true
        }

        binding.etDeliveryDate.setOnClickListener{
            val datePicker = DataPickerFragment{year, month, day ->
                val mp = month+1
                var m = ""
                var d = ""
                if(mp < 10) m = "0$mp" else m = "$mp"
                if(day < 10) d = "0$day" else d = "$day"

                binding.etDeliveryDate.setText("$d/$m/$year")
            }
            datePicker.show(parentFragmentManager, "datePicker")
        }

        binding.btnGoPay.setOnClickListener {
            if(!binding.rbOne.isChecked) if(binding.etOtherAddress.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etCellphoneContact.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etDeliveryDate.text.toString().isEmpty()) return@setOnClickListener

            var deliveryPlace = ""

            binding.apply {
                deliveryPlace = if(rbOne.isChecked) currentAddressUser else etOtherAddress.text.toString()

            }

            val bundle = Bundle().apply {
                putString("KEY_PLACE_DELIVERY", deliveryPlace)
                putString("KEY_CELLPHONE", binding.etCellphoneContact.text.toString())
                putString("KEY_DELIVERY_DATE", binding.etDeliveryDate.text.toString())
                putParcelableArrayList("KEY_AL_SC", arLstSC)
            }

            parentFragmentManager.commit {
                replace<PayFragment>(R.id.flFrameContent, args = bundle)
                setReorderingAllowed(true)
                addToBackStack("Pay")
            }

        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeliveryInformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}