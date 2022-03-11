package com.example.businessfinder.scenes.analysis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.databinding.FragmentAnalysisBinding

class AnalysisFragment : Fragment(R.layout.fragment_analysis) {
    private val binding: FragmentAnalysisBinding by viewBinding(FragmentAnalysisBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGraphs()
    }

    private fun setupGraphs() {

    }
}