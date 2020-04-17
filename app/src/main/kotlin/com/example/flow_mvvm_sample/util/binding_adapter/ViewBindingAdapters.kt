package com.example.flow_mvvm_sample.util.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisibleGone(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}