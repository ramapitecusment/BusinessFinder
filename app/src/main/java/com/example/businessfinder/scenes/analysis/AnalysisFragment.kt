package com.example.businessfinder.scenes.analysis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.databinding.FragmentAnalysisBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class AnalysisFragment : Fragment(R.layout.fragment_analysis) {
    private val binding: FragmentAnalysisBinding by viewBinding(FragmentAnalysisBinding::bind)
    private val overallOffers: Float = 100f
    private val acceptedOffers: Float = 70f
    private val offersResponded: Float = 52f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGraphs()
    }

    private fun setupGraphs() {
        setupLineGraph()
        setupPieGraph()
        binding.offersRespond.text =
            String.format(getString(R.string.offer_respond), (offersResponded / overallOffers * 100))
        binding.offersAccepted.text =
            String.format(getString(R.string.offer_accepted), (acceptedOffers / overallOffers * 100))
    }

    private fun setupLineGraph() {
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .categories(dates)
            .yAxisVisible(false)
            .series(activities)
        binding.activityBar.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun setupPieGraph() {
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .series(
                arrayOf(
                    AASeriesElement()
                        .data(
                            arrayOf(
                                arrayOf("Откликнулось", offersResponded),
                                arrayOf("Всего", overallOffers)
                            )
                        )
                )
            )
        binding.offersRespondPie.aa_drawChartWithChartModel(aaChartModel)
    }
}