package com.example.virtualshop.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.virtualshop.R
import com.example.virtualshop.database.AppDataBase
import com.example.virtualshop.databinding.FragmentDetailProductBinding
import com.example.virtualshop.model.Product
import com.example.virtualshop.model.ShoppingCart
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailProductFragment : Fragment() {
    private lateinit var binding: FragmentDetailProductBinding

    private var product: Product? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable("KEY_PRODUCT")
            //param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailProductBinding.inflate(inflater, container, false)

        val urlBase = "https://funny-trench-coat-hen.cyclic.app/api/1.0/public"


        binding.apply {
            product?.let{
                it.apply {
                    Picasso.get().load(urlBase+urlImg)
                        .resize(220,220)
                        .error(R.drawable.ic_launcher_background)
                        .into(ivImgDetail)
                    tvNameProductDetail.text = nameProduct
                    tvDescriptionDetail.text = description
                    tvPriceDetail.text = "S/. $unitPrice"
                }

            }

        }


        binding.fabAddProduct.setOnClickListener {
            val shoppingCart = ShoppingCart(
                id = 0,
                idProduct = product!!.idProduct,
                nameProduct = product!!.nameProduct,
                urlImg = product!!.urlImg,
                unitPrice = product!!.unitPrice,
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

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}