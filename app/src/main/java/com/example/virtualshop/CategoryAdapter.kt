package com.example.virtualshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualshop.databinding.ItemCategoryHomeBinding
import com.example.virtualshop.model.Category

class CategoryAdapter (var categories : List<Category> = emptyList(), val onItemListByCategory:(Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding : ItemCategoryHomeBinding = ItemCategoryHomeBinding.bind(itemView)

        fun bind(category: Category){
            //binding.ivPhotoCategory = category.
            binding.tvNameCategory.text = category.description

            binding.root.setOnClickListener{
                onItemListByCategory(category)
            }
        }
    }

    fun updateList(categories:List<Category>){
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_category_home, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }
}