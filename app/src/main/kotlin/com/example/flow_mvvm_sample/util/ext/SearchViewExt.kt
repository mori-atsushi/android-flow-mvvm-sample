package com.example.flow_mvvm_sample.util.ext

import androidx.appcompat.widget.SearchView

fun SearchView.requestQuery(query: String) {
    val current = this.query.toString()
    if (current != query) {
        setQuery(query, false)
    }
}