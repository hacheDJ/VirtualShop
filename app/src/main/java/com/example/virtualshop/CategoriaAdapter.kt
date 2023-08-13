package com.example.virtualshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualshop.databinding.ItemCategoriaBinding
import com.squareup.picasso.Picasso


class CategoriaAdapter(var categorias:List<Categoria> = emptyList())
    : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            private val binding : ItemCategoriaBinding = ItemCategoriaBinding.bind(itemView)

            fun bind(categoria:Categoria) = with(binding){

                    tvNombreCategoria.text = categoria.descripcion
                    Picasso.get().load(categoria.foto).into(imageCategoria)

            }
        }

    fun updateList(categorias: List<Categoria>){
        this.categorias = categorias
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val categoria = categorias[position]
        holder.bind(categoria)
    }


}