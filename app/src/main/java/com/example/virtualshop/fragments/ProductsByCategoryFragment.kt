package com.example.virtualshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.virtualshop.ProductAdapter
import com.example.virtualshop.R
import com.example.virtualshop.database.AppDataBase
import com.example.virtualshop.databinding.FragmentProductsByCategoryBinding
import com.example.virtualshop.model.ShoppingCart
import com.example.virtualshop.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductsByCategoryFragment : Fragment() {
    private lateinit var binding: FragmentProductsByCategoryBinding
    private var productAdapter = ProductAdapter(onItemDetail = {product ->
        val bundle = Bundle()
        bundle.putParcelable("KEY_PRODUCT", product)

        parentFragmentManager.commit {
            replace<DetailProductFragment>(R.id.flFrameContent, args = bundle)
            setReorderingAllowed(true)
            addToBackStack("DetailProduct")
        }
    }){product ->
        val shoppingCart = ShoppingCart(
            id = 0,
            idProduct = product.idProduct,
            nameProduct = product.nameProduct,
            urlImg = product.urlImg,
            unitPrice = product.unitPrice,
            quantity = 1
        )

        var lstSC = emptyList<ShoppingCart>()

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                lstSC = AppDataBase.getInstance(requireActivity()).shoppingCartDAO().findByIdProduct(shoppingCart.idProduct)

            }

            if(lstSC.isEmpty()){
                withContext(Dispatchers.IO){
                    AppDataBase.getInstance(requireActivity()).shoppingCartDAO().insert(shoppingCart)

                }

                Toast.makeText(context, "Agregado al carrito ", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Ya esta agregado ", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private var nameCategory: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nameCategory = it.getString("KEY_NAME_CATEGORY")
            param2 = it.getString(ARG_PARAM2)
        }

        try {
            /**SERACH FROM THE CATEGORY**/
            lifecycleScope.launch{
                val response = withContext(Dispatchers.IO){
                    Api.build().getProductsByCategory(nameCategory!!)
                }

                if(response.isSuccessful){
                    val lstPro = response.body()

                    lstPro?.let{
                        productAdapter.updateList(it)
                    }
                }

            }

        }catch(ex: Exception){
            println(ex.message)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsByCategoryBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(this.context, 2)
        binding.rvResultFindProductsByCategory.layoutManager = layoutManager
        binding.rvResultFindProductsByCategory.adapter = productAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductsByCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}