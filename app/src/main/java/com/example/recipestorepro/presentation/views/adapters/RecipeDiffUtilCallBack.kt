package com.example.recipestorepro.presentation.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.recipestorepro.domain.models.RecipeItem

class RecipeDiffUtilCallBack(
    private val oldList: List<RecipeItem>,
    private val newList: List<RecipeItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].id == oldList[oldItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]

}