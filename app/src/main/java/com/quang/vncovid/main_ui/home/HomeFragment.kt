package com.quang.vncovid.main_ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentHomeBinding
import com.quang.vncovid.custom_ui.CustomMarkerView
import com.quang.vncovid.util.formatNumber


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeViewModel.getSumPatient()
        homeViewModel.getChartCovid()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val lineChart = binding.lineChart

        homeViewModel.sumPatient.observe(viewLifecycleOwner) {
            binding.tvConfirm.text = formatNumber(it.confirmed)
            binding.tvPlusConfirm.text = formatNumber(it.plusConfirmed)

            val curingNumber = it.confirmed - it.recovered - it.death
            val plusCuringNumber = it.plusConfirmed - it.plusRecovered - it.plusDeath
            binding.tvCuring.text = formatNumber(if (curingNumber > 0) curingNumber else 0)
            binding.tvPlusCuring.text =
                formatNumber(if (plusCuringNumber > 0) plusCuringNumber else 0)

            binding.tvRecovered.text = formatNumber(it.recovered)
            binding.tvPlusRecovered.text = formatNumber(it.plusRecovered)

            binding.tvDeath.text = formatNumber(it.death)
            binding.tvPlusDeath.text = formatNumber(it.plusDeath)
        }

        lineChart.setScaleEnabled(false)
        lineChart.description = Description().apply { text = "" }
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.axisMinimum = 0f
        lineChart.extraBottomOffset = 10f
        lineChart.data = LineData()
        lineChart.axisLeft.textSize = 12f
        lineChart.xAxis.textSize = 12f
        lineChart.legend.textSize = 12f

        homeViewModel.chartCovidList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                lineChart.marker = CustomMarkerView(context, R.layout.custom_marker_view)

                val chartCovidList = it.subList(it.size - 7, it.size)
                var biggestNumber = 0f

                // Số ca nhiễm
                val confirmedLineList = mutableListOf<Entry>()
                // Đã khỏi bệnh
                val recoveredLineList = mutableListOf<Entry>()

                chartCovidList.forEachIndexed { index, chartCovidModel ->
                    if (biggestNumber < chartCovidModel.confirmed)
                        biggestNumber = chartCovidModel.confirmed.toFloat()
                    if (biggestNumber < chartCovidModel.recovered)
                        biggestNumber = chartCovidModel.recovered.toFloat()

                    confirmedLineList.add(
                        Entry(
                            index.toFloat(),
                            chartCovidModel.confirmed.toFloat()
                        )
                    )

                    recoveredLineList.add(
                        Entry(
                            index.toFloat(),
                            chartCovidModel.recovered.toFloat()
                        )
                    )
                }

                val confirmedLineDataSet = LineDataSet(confirmedLineList, "Ca nhiễm")
                confirmedLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                confirmedLineDataSet.setDrawValues(false)
                context?.let { ContextCompat.getColor(it, R.color.color_confirmed) }
                    ?.let {
                        confirmedLineDataSet.color = it
                        confirmedLineDataSet.setCircleColor(it)
                    }

                val recoveredLineDataSet = LineDataSet(recoveredLineList, "Đã khỏi")
                recoveredLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                recoveredLineDataSet.setDrawValues(false)
                context?.let { ContextCompat.getColor(it, R.color.color_recovered) }
                    ?.let {
                        recoveredLineDataSet.color = it
                        recoveredLineDataSet.setCircleColor(it)
                    }

//                // Tử vong
//                val deathLineList = mutableListOf<Entry>()
//                chartCovidList.forEachIndexed { index, chartCovidModel ->
//                    deathLineList.add(
//                        Entry(
//                            index.toFloat(),
//                            chartCovidModel.death.toFloat()
//                        )
//                    )
//                }
//                val deathLineDataSet = LineDataSet(deathLineList, "Tử vong")
//                deathLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//                deathLineDataSet.setDrawValues(false)
//                context?.let { ContextCompat.getColor(it, R.color.color_death) }
//                    ?.let {
//                        deathLineDataSet.color = it
//                        deathLineDataSet.setCircleColor(it)
//                    }

                lineChart.axisLeft.axisMaximum = (biggestNumber * 1.2).toFloat()
                lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return chartCovidList[value.toInt()].date
                    }
                }
                lineChart.data = LineData(
                    mutableListOf<ILineDataSet>(
                        confirmedLineDataSet,
                        recoveredLineDataSet,
//                        deathLineDataSet
                    )
                )
                lineChart.invalidate()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

