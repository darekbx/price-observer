package com.darekbx.priceobserver.ui.itemslist

import android.app.Dialog
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.darekbx.priceobserver.R
import kotlinx.android.synthetic.main.dialog_url_select.*

class UrlSelectDialog : DialogFragment() {

    var onSelected: ((url: String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_url_select, container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        val back = ColorDrawable(Color.WHITE)
        val inset = InsetDrawable(back, 20)
        dialog.window?.setBackgroundDrawable(inset)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_button.setOnClickListener {
            onSelected?.invoke(url_field.text.toString())
            dismiss()
        }
        cancel_button.setOnClickListener { dismiss() }
        paste_button.setOnClickListener { paste() }
    }

    private fun paste() {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboardManager.hasPrimaryClip()){
            val input = clipboardManager.primaryClip
                ?.takeIf { it.itemCount > 0 }
                ?.getItemAt(0)
                ?.text
            url_field.setText(input)
        }
    }
}
