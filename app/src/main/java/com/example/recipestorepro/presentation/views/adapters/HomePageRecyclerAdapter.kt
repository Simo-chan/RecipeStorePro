package com.example.recipestorepro.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.ItemsOfRecipeRecyclerViewBinding
import com.example.recipestorepro.domain.models.RecipeItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomePageRecyclerAdapter : RecyclerView.Adapter<HomePageRecyclerAdapter.ViewHolder>() {
    var onItemClick: ((RecipeItem) -> Unit)? = null
    var recipeDataSet = emptyList<RecipeItem>()

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
            binding.creationDate.text = milliToDate(recipe.date)
            binding.recipeSynced.setBackgroundResource(
                if (recipe.remotelyConnected) R.drawable.synced
                else R.drawable.not_sync
            )
            if (recipe.isFavorite) {
                binding.starIcon.isVisible = true
            }

            itemView.setOnClickListener { onItemClick?.invoke(recipe) }
        }
    }

    private fun milliToDate(time: Long): String {
        val date = Date(time)
        val simpleDateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
}