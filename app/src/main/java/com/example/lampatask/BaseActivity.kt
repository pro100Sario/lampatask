package com.example.lampatask

import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity: AppCompatActivity() {

    private var progressDialog: AlertDialog? = null
    private var errorDialog: AlertDialog? = null

    fun showProgress() {
        if (progressDialog != null) {
            progressDialog?.show()
        } else {
            progressDialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.loading)
                .setMessage(R.string.please_wait)
                .setCancelable(false)
                .setView(ProgressBar(this).apply {
                    setPadding(0, 0, 0, 50)
                })
                .create()
            progressDialog?.show()
        }
    }

    fun hideProgress() {
        progressDialog?.hide()
    }

    fun showError(retry: () -> Unit) {
        if (errorDialog != null) {
            errorDialog?.show()
        } else {
            errorDialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.error_message)
                .setCancelable(false)
                .setPositiveButton(R.string.retry) { d, w ->
                    retry.invoke()
                }
                .setNegativeButton(R.string.exit) { d, w ->
                    finish()
                }
                .create()
            errorDialog?.show()
        }

    }

}