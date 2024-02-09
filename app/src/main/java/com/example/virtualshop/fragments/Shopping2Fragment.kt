package com.example.virtualshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualshop.R
import com.example.virtualshop.ShoppingCartAdapter
import com.example.virtualshop.database.AppDataBase
import com.example.virtualshop.databinding.FragmentShopping2Binding
import com.example.virtualshop.model.ShoppingCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Shopping2Fragment : Fragment() {
    private lateinit var binding: FragmentShopping2Binding
    private var shoppingCartAdapter = ShoppingCartAdapter(
        onQuantityAdd = {shoppingCart ->
            val q = shoppingCart.quantity+1
            val objSC = ShoppingCart(
                id = shoppingCart.id,
                idProduct = shoppingCart.idProduct,
                nameProduct = shoppingCart.nameProduct,
                urlImg = shoppingCart.urlImg,
                unitPrice = shoppingCart.unitPrice,
                quantity = q
            )

            lifecycleScope.launch{
                withContext(Dispatchers.IO){
                    AppDataBase.getInstance(requireActivity()).shoppingCartDAO().update(objSC)
                }

            }
    },
        onQuantityDecrease = {shoppingCart ->
            if(shoppingCart.quantity >= 2){
                val q = shoppingCart.quantity-1
                val objSC = ShoppingCart(
                    id = shoppingCart.id,
                    idProduct = shoppingCart.idProduct,
                    nameProduct = shoppingCart.nameProduct,
                    urlImg = shoppingCart.urlImg,
                    unitPrice = shoppingCart.unitPrice,
                    quantity = q
                )

                lifecycleScope.launch{
                    withContext(Dispatchers.IO){
                        AppDataBase.getInstance(requireActivity()).shoppingCartDAO().update(objSC)
                    }

                }
            }

    }){shoppingCart ->
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                AppDataBase.getInstance(requireActivity()).shoppingCartDAO().delete(shoppingCart)
            }

        }

    }

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
        binding = FragmentShopping2Binding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.rvListProductShoppingCart.layoutManager = layoutManager
        binding.rvListProductShoppingCart.adapter = shoppingCartAdapter

        AppDataBase.getInstance(requireContext()).shoppingCartDAO().getAll().observe(viewLifecycleOwner){
            shoppingCartAdapter.updateList(it)

            /**CALCULATING AMOUNT SHOPPING CART**/
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            var total = 0.00

            it.forEach{sc ->
                total += sc.unitPrice * sc.quantity

            }

            var subTotal = df.format(total * 0.82)
            var igv = df.format(total * 0.18)
            var totalF = df.format(total)

            binding.tvValueSubtotal.text = "S/. $subTotal"
            binding.tvValueIgv.text = "S/. $igv"
            binding.tvValueTotal.text = "S/. $totalF"
        }

        /**BUTTON THAT SEND TO THE FRAGMENT DELIVERY INFORMATION**/
        binding.btnConfirmPurchase.setOnClickListener {
            var lstSC: List<ShoppingCart> = listOf<ShoppingCart>()
            val arListSC = arrayListOf<ShoppingCart>()

            lifecycleScope.launch {
                 withContext(Dispatchers.IO){
                     lstSC = AppDataBase.getInstance(requireActivity()).shoppingCartDAO().getAllProducts()
                     arListSC.addAll(lstSC)
                }

            }

            val bundle = Bundle()
            bundle.putParcelableArrayList("KEY_ARRAYLIST_SC", arListSC)

            parentFragmentManager.commit {
                replace<DeliveryInformationFragment>(R.id.flFrameContent, args= bundle)
                setReorderingAllowed(true)
                addToBackStack("DeliveryInformation")
            }
            
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Shopping2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}