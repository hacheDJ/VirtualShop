package com.example.virtualshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualshop.databinding.ItemProductBinding
import com.example.virtualshop.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(var products : List<Product> = emptyList(), val onItemDetail:(Product) -> Unit, val onItemAddToCart:(Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemProductBinding = ItemProductBinding.bind(itemView)
        private val urlBase = "https://funny-trench-coat-hen.cyclic.app/api/1.0/public"

        fun bind(product: Product) = with(binding){
            tvNameProduct.text = product.nameProduct
            tvDescriptionProduct.text = product.description
            tvPriceProduct.text = "S/. "+product.unitPrice
            Picasso.get().load(urlBase+product.urlImg)
                .resize(120,120)
                .error(R.drawable.ic_launcher_background)
                .into(ivProduct)

            fabAddProduct.setOnClickListener {
                onItemAddToCart(product)
            }

            root.setOnClickListener{
                onItemDetail(product)
            }
        }

    }

    fun updateList(products:List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }
}