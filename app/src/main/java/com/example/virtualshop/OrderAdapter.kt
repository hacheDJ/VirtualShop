package com.example.virtualshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualshop.databinding.ItemOrderBinding
import com.example.virtualshop.databinding.ItemProductBinding
import com.example.virtualshop.model.Order

class OrderAdapter (var orders : List<Order> = emptyList(), val onItemDetail:(Order) -> Unit) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemOrderBinding = ItemOrderBinding.bind(itemView)

        fun bind(order: Order) = with(binding){
            var orderState = ""
            when(order.orderState){
                "pending" -> orderState = "Pendiente"
                "on the way" -> orderState = "En camino"
                "delivered" -> orderState = "Entegado"
                else -> "No especificado"
            }

            tvOrderState.text = orderState
            tvDeliveyDate.text = order.deliveryDate.split("T")[0]
            tvOrderDate.text = "Pedido el "+order.orderDate.split("T")[0]
            tvIdOrder.text = "Id del pedido: "+order.idOrder.toString()
            tvSeller.text = "Vendedor: "+order.seller
            tvTotalOrder.text = "S/. "+order.total.toString()

            root.setOnClickListener{
                onItemDetail(order)
            }
        }

    }

    fun updateList(orders:List<Order>){
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }
}