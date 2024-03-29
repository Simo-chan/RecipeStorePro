package com.example.recipestorepro.adapters

import androidx.recyclerview.widget.DiffUtil

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