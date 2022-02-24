package com.example.businessfinder.common.extensions

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchWhenStarted(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner .repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@launchWhenStarted.collect()
        }
    }
}

