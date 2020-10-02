package com.darekbx.priceobserver.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.darekbx.priceobserver.R
import kotlinx.android.synthetic.main.dialog_tag_select.*

class TagSelectDialog : DialogFragment() {

    companion object {
        val HTML_PART = "html_part"
    }

    var onCompleted: ((name: String, partToObserve: String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_tag_select, container)
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

        arguments?.getString(HTML_PART)?.let { text ->
            text_field.setText(text)
        }

        save_button.setOnClickListener {
            onCompleted?.invoke(
                name_field.text.toString(),
                text_field.text.toString()
            )
            dismiss()
        }

        cancel_button.setOnClickListener {
            dismiss()
        }
    }
}
