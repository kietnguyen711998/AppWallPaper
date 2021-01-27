package com.kn.appwallpaper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kn.appwallpaper.R
import com.kn.appwallpaper.model.Category
import kotlinx.android.synthetic.main.cat_item.view.*

class CategoriesAdapter(
    var categories: List<Category>,
    private val context: Context,
    private var clickListener: OnItemClickedListener
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.category_image
        private val name = view.category_name

        fun bind(category: Category, context: Context, action: OnItemClickedListener) {
            name.text = category.name
            Glide.with(context).asBitmap().load(category.image_url).into(imageView)

            name.setOnClickListener {
                action.onCategoryItemCLicked(category)
            }
        }
    }

    interface OnItemClickedListener {
        fun onCategoryItemCLicked(category: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cat_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], context, clickListener)
    }

}