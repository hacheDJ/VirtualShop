package com.example.virtualshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualshop.databinding.ItemShoppingCartBinding
import com.example.virtualshop.model.ShoppingCart
import com.squareup.picasso.Picasso

class ShoppingCartAdapter (var shoppingCarts : List<ShoppingCart> = emptyList(), val onQuantityAdd:(ShoppingCart) -> Unit, val onQuantityDecrease:(ShoppingCart) -> Unit, val onItemDelete:(ShoppingCart) -> Unit) : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>(){
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemShoppingCartBinding = ItemShoppingCartBinding.bind(itemView)
        private val urlBase = "https://funny-trench-coat-hen.cyclic.app/api/1.0/public"

        fun bind(shoppingCart: ShoppingCart) = with(binding){
            Picasso.get().load(urlBase+shoppingCart.urlImg)
                .resize(100,100)
                .error(R.drawable.ic_launcher_background)
                .into(ivPhotoSC)
            tvNameProductSC.text = shoppingCart.nameProduct
            tvPriceProductSC.text = "S/. "+shoppingCart.unitPrice.toString()
            etQuantity.setText(shoppingCart.quantity.toString())

            ivAddQuantity.setOnClickListener {
                onQuantityAdd(shoppingCart)
            }

            ivDecreaseQuantity.setOnClickListener {
                onQuantityDecrease(shoppingCart)
            }

            ivBtnDelete.setOnClickListener {
                onItemDelete(shoppingCart)
            }

        }

    }

    fun updateList(shoppingCarts:List<ShoppingCart>){
        this.shoppingCarts = shoppingCarts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_cart, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return shoppingCarts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingCart = shoppingCarts[position]
        holder.bind(shoppingCart)
    }
}