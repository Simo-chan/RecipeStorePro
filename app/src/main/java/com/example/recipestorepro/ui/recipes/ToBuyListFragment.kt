package com.example.recipestorepro.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentTobuyListBinding

class ToBuyListFragment : Fragment(R.layout.fragment_tobuy_list) {

    private var _binding: FragmentTobuyListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTobuyListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}