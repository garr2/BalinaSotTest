package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.utils

import android.databinding.BindingAdapter

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


@BindingAdapter("visibility")
fun View.visibility(visibility: Boolean) {
    this.visibility = if(visibility) View.VISIBLE else View.GONE
}

@BindingAdapter("app:adapter")
fun AutoCompleteTextView.setAutoCompleteAdapter(array: ArrayAdapter<String>) {
    this.setAdapter(array)
}