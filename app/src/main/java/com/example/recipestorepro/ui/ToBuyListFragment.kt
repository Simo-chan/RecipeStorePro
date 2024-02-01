package com.example.recipestorepro.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recipestorepro.R

class ToBuyListFragment : Fragment(R.layout.tobuy_list_fragment) {

    private var toBuyListTextView: TextView? = null
    private var toBuyListEditText: EditText? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        toBuyListTextView = view.findViewById(R.id.toBuyListText)
        toBuyListEditText = view.findViewById(R.id.toBuyListInput)

        toBuyListTextView?.setOnClickListener {
            toBuyListTextView!!.visibility = View.GONE
            toBuyListEditText!!.visibility = View.VISIBLE
            toBuyListEditText!!.setText(toBuyListTextView!!.text)
            toBuyListEditText!!.requestFocus()
        }

        toBuyListEditText?.setOnEditorActionListener { _, _, _ ->
            toBuyListTextView!!.visibility = View.VISIBLE
            toBuyListEditText!!.visibility = View.GONE
            toBuyListTextView!!.text = toBuyListEditText!!.text
            true
        }

    }
}