package com.example.virtualshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.room.FtsOptions.Order
import com.example.virtualshop.R
import com.example.virtualshop.databinding.FragmentPayBinding
import com.example.virtualshop.dto.PayReq
import com.example.virtualshop.dto.ProofOfPayDetailReq
import com.example.virtualshop.model.ShoppingCart
import com.example.virtualshop.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PayFragment : Fragment() {
    private lateinit var  binding: FragmentPayBinding

    private var placeDelivery: String? = null
    private var cellPhone: String? = null
    private var deliveryDate: String? = null
    private var alSC: ArrayList<ShoppingCart>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeDelivery = it.getString("KEY_PLACE_DELIVERY")
            cellPhone = it.getString("KEY_CELLPHONE")
            deliveryDate = it.getString("KEY_DELIVERY_DATE")
            alSC = it.getParcelableArrayList("KEY_AL_SC")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayBinding.inflate(layoutInflater, container, false)

        val typeCardList: List<String> = listOf("crédito", "débito")

        binding.spCardType.setAdapter(ArrayAdapter(requireActivity(), R.layout.item_type_card, typeCardList))

        binding.btnPay.setOnClickListener {
            if(binding.spCardType.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etCardNumber.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etHolderName.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etDateExpiry.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etCvv.text.toString().isEmpty()) return@setOnClickListener
            if(binding.etPasswordCard.text.toString().isEmpty()) return@setOnClickListener

            val tcard = if(binding.spCardType.text.toString() == "crédito") "credit" else "debit"

            var listProofOfPayDetailReq: List<ProofOfPayDetailReq> = listOf()
            var totalAmount = 0.0
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN

            alSC?.forEach {
                val objProofOfPayDetailReq = ProofOfPayDetailReq(
                    idProduct = it.idProduct,
                    quantity = it.quantity,
                    salePrice = it.unitPrice
                )

                totalAmount += it.unitPrice * it.quantity

                listProofOfPayDetailReq += objProofOfPayDetailReq
            }

            val d = deliveryDate!!.substring(0,2)
            val m = deliveryDate!!.substring(3,5)
            val y = deliveryDate!!.substring(6,8)

            val date = "20$y-$m-$d"


            val payReq = PayReq(
                cardType = tcard,
                cardNumber = binding.etCardNumber.text.toString(),
                holder = binding.etHolderName.text.toString(),
                dateOfExpiry = binding.etDateExpiry.text.toString(),
                cvv = binding.etCvv.text.toString(),
                passCard = binding.etPasswordCard.text.toString(),
                totalAmount = df.format(totalAmount).toDouble(),
                listProofOfPayDetail = listProofOfPayDetailReq,
                placeOfDelivery = placeDelivery.toString(),
                deliveryDate = date,
                contactNumber = cellPhone.toString()
            )

            lifecycleScope.launch {
                val response = withContext(Dispatchers.IO){
                    Api.build().pay(payReq)
                }

                if(response.isSuccessful){
                    val payRes = response.body()

                    if(payRes!!.err){
                        Toast.makeText(requireActivity(), payRes.data, Toast.LENGTH_LONG).show()
                    }
                    else{
                        parentFragmentManager.commit {
                            replace<OrderFragment>(R.id.flFrameContent)
                            setReorderingAllowed(true)
                            addToBackStack("Order")
                        }

                    }
                }

            }

        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}