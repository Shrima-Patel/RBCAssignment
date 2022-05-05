package com.rbc.rbcmobileapplication.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.rbc.rbcmobileapplication.R

object CustomAlertDialog {
    fun showAlertDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}