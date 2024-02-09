package com.example.virtualshop.fragments

import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualshop.CategoryAdapter
import com.example.virtualshop.ProductAdapter
import com.example.virtualshop.R
import com.example.virtualshop.database.AppDataBase
import com.example.virtualshop.databinding.FragmentHome2Binding
import com.example.virtualshop.model.ShoppingCart
import com.example.virtualshop.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home2Fragment : Fragment() {

    private lateinit var binding: FragmentHome2Binding
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

    private var categoryAdapter = CategoryAdapter(){category ->
        val bundle = Bundle()
        bundle.putString("KEY_NAME_CATEGORY", category.description)

        parentFragmentManager.commit {
            replace<ProductsByCategoryFragment>(R.id.flFrameContent, args = bundle)
            setReorderingAllowed(true)
            addToBackStack("ProductsByCategory")
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

        binding = FragmentHome2Binding.inflate(inflater, container, false)

        val layoutManager = object: GridLayoutManager(this.context, 2){
            override fun canScrollHorizontally() : Boolean {
                return true
            }
            override fun canScrollVertically() : Boolean {
                return false
            }

        }
        binding.rvCategories.layoutManager = layoutManager
        binding.rvCategories.adapter = categoryAdapter

        val layoutManager2 = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListProducts.layoutManager = layoutManager2
        binding.rvListProducts.adapter = productAdapter


        /*
        * Categories
        * List
        */
        lifecycleScope.launch {
            try {
                binding.pbCategory.visibility = View.VISIBLE

                val response = withContext(Dispatchers.IO){
                    Api.build().getCategories()
                }

                if(response.isSuccessful){
                    val lstCat = response.body()

                    lstCat?.let{
                        categoryAdapter.updateList(it)
                    }
                }

            }catch (ex: Exception){
                println(ex.message)
            }finally {
                binding.pbCategory.visibility = View.GONE
            }
        }

        /*
        * Products
        * List
        */
        lifecycleScope.launch {
            try {
                binding.pbProduct.visibility = View.VISIBLE

                val response = withContext(Dispatchers.IO){
                    Api.build().getProductsByCategory("ropa")
                }

                if(response.isSuccessful){
                    val lstPro = response.body()

                    lstPro?.let{
                        productAdapter.updateList(it)
                    }
                }

            }catch (ex: Exception){
                println(ex.message)
            }finally {
                binding.pbProduct.visibility = View.GONE
            }
        }


        /*
        *
        **/


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}