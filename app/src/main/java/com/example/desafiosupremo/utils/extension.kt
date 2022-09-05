package com.example.desafiosupremo.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        requireContext(),
        message,
        duration
    ).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun dateFormater(date: String, format: String): String {
    val dateReciver = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val dateParse = dateReciver.parse(date)
    val formatDate = format
    val originalDate = SimpleDateFormat(formatDate)
    return originalDate.format(dateParse)
}

