package com.darekbx.priceobserver.ui.item

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
import kotlinx.android.synthetic.main.dialog_tag_select.cancel_button
import kotlinx.android.synthetic.main.dialog_tag_select.save_button

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
            html_field.setText(text)
        }

        save_button.setOnClickListener { saveNewItem() }
        cancel_button.setOnClickListener { dismiss() }
    }

    private fun saveNewItem() {
        val name = name_field.text.toString()
        val html = html_field.text.toString()

        if (name.isBlank()) {
            name_field.setError(getString(R.string.validation_error))
            return
        }

        if (html.isBlank()) {
            html_field.setError(getString(R.string.validation_error))
            return
        }

        onCompleted?.invoke(name, html)
        dismiss()
    }
}
