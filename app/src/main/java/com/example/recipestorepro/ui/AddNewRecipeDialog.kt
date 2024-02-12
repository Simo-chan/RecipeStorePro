package com.example.recipestorepro.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.DialogAddNewRepiceBinding
import com.example.recipestorepro.databinding.FragmentCreateNewRecipeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewRecipeDialog : BottomSheetDialogFragment(R.layout.dialog_add_new_repice) {

    private var _binding: DialogAddNewRepiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddNewRepiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpClickListeners() {
        binding.createRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_addNewRecipeFragment_to_createNewRecipeFragment)
        }
    }
}