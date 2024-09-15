package com.example.recipestorepro.presentation.views.fragments.account

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.DialogGiveFeedbackBinding

class GiveFeedbackDialog : DialogFragment() {
    private var _binding: DialogGiveFeedbackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomWidthDialogTheme)
            _binding = DialogGiveFeedbackBinding.inflate(layoutInflater)
            val dialog = builder.setView(binding.root).create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.custom_background_feedback_dialog)
            setOnClickListeners()
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setOnClickListeners() {
        binding.telegramIcon.setOnClickListener {
            onTelegramClicked()
        }

        binding.emailIcon.setOnClickListener {
            onEmailClicked()
        }

        binding.cancelIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun onTelegramClicked() {
        val uri: Uri = Uri.parse(TELEGRAM_ADDRESS)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                getString(R.string.no_browser_found),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onEmailClicked() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(MAIL_TO_URI)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_ADDRESS))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject))
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                getString(R.string.no_email_found),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    companion object {
        const val MAIL_TO_URI = "mailto:"
        const val TELEGRAM_ADDRESS = "https://t.me/Simon_1"
        const val EMAIL_ADDRESS = "simoegorov@gmail.com"
    }
}