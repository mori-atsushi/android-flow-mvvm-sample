package com.example.flow_mvvm_sample.util.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

fun Collection<Flow<Any?>>.launchAllIn(scope: CoroutineScope) {
    map { it.launchIn(scope) }
}