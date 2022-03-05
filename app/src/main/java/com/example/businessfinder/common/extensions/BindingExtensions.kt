package com.example.businessfinder.common.extensions

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

fun Fragment.bindText(stateFlow: StateFlow<String>, textView: TextView) =
    stateFlow.onEach {
        textView.text = it
    }.launchWhenStarted(viewLifecycleOwner)

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

fun Fragment.bindImage(
    stateFlow: StateFlow<String>,
    imageView: ImageView
) = stateFlow.onEach { imageView.glideImage(it) }
    .launchWhenStarted(viewLifecycleOwner)

fun Fragment.bindFirebaseImage(
    stateFlow: StateFlow<String>,
    imageView: ImageView
) = stateFlow.onEach {
    if (it.isEmpty()) return@onEach
    imageView.glideFirebaseImage(it)
}.launchWhenStarted(viewLifecycleOwner)