package com.example.businessfinder.common.extensions

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

inline fun <T> Fragment.bind(stateFlow: StateFlow<T>, crossinline block: (T) -> Unit) =
    stateFlow.onEach { value ->
        value?.let { block(it) }
    }.launchWhenStarted(viewLifecycleOwner)

inline fun <T> Fragment.bindAction(stateFlow: SharedFlow<T>, crossinline block: (T) -> Unit) =
    stateFlow.onEach { value ->
        value?.let { block(it) }
    }.launchWhenStarted(viewLifecycleOwner)

fun Fragment.bindTextTwoWay(stateFlow: MutableStateFlow<String>, editText: EditText) {
    editText.doOnTextChanged { text, _, _, _ ->
        stateFlow.value = text.toString()
    }

    stateFlow.onEach {
        if (editText.text.toString() == it) {
            return@onEach
        }

        val oldSelection = editText.selectionStart
        val newLength = it.length
        val oldLength = editText.text?.length ?: 0
        val diff = newLength - oldLength
        editText.setText(it)

        var newSelection = when (diff) {
            1, -1 -> oldSelection + diff
            else -> newLength
        }

        if (newSelection < 0) {
            newSelection = 0
        }

        try {
            editText.setSelection(newSelection)
        } catch (e: Exception) {
            print(e)
        }
    }.launchWhenStarted(viewLifecycleOwner)
}