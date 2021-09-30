package com.example.lampatask

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .into(this)
}

fun Activity.openInBrowser(content: Content) {
    try {
        if (content.getUrlForOpen().isBlank()) {
            Toast.makeText(this, R.string.empty_link, Toast.LENGTH_SHORT).show()
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(content.getUrlForOpen()))
            startActivity(browserIntent)
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        Toast.makeText(this, R.string.error_open, Toast.LENGTH_SHORT).show()
    }

}