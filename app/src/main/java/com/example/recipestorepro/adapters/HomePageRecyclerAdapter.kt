package com.example.recipestorepro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorepro.R

class HomePageRecyclerAdapter : RecyclerView.Adapter<HomePageRecyclerAdapter.ViewHolder>() {
    var onItemClick: ((RecipeItem) -> Unit)? = null
    private var recipeDataSet = emptyList<RecipeItem>()

    fun updateRecipeDataSet(recipeData: List<RecipeItem>) {
        val recipeDiffUtil = RecipeDiffUtilCallBack(recipeDataSet, recipeData)
        val diffResult = DiffUtil.calculateDiff(recipeDiffUtil)
        diffResult.dispatchUpdatesTo(this)
        recipeDataSet = recipeData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_of_recipe_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = recipeDataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeDataSet[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.recipeTitle)
        private val date: TextView = itemView.findViewById(R.id.creationDate)

        fun bind(recipe: RecipeItem) {
            title.text = recipe.title
            date.text = recipe.date.toString()

            itemView.setOnClickListener { onItemClick?.invoke(recipe) }
        }
    }
}