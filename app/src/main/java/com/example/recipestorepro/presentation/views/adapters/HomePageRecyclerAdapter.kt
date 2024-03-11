package com.example.recipestorepro.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorepro.databinding.ItemsOfRecipeRecyclerViewBinding

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
        val binding = ItemsOfRecipeRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recipeDataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeDataSet[position])
    }


    inner class ViewHolder(private val binding: ItemsOfRecipeRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeItem) {
            binding.recipeTitle.text = recipe.title
            binding.creationDate.text = recipe.date.toString()

            itemView.setOnClickListener { onItemClick?.invoke(recipe) }
        }
    }
}