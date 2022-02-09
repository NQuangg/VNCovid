package com.quang.vncovid.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.MPPointF
import com.quang.vncovid.R
import com.quang.vncovid.databinding.FragmentHomeBinding
import com.quang.vncovid.ui.custom_ui.CustomMarkerView
import com.quang.vncovid.util.formatNumber
import java.text.DecimalFormat


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
        val swipeRefresh = binding.swipeRefresh

        context?.let { ContextCompat.getColor(it, R.color.color_primary) }
            ?.let { swipeRefresh.setColorSchemeColors(it) }
        swipeRefresh.setOnRefreshListener {
            binding.tvConfirm.text = "###.###"
            binding.tvPlusConfirm.text = "###.###"
            binding.tvCuring.text = "###.###"
            binding.tvPlusCuring.text = "###.###"
            binding.tvRecovered.text = "###.###"
            binding.tvPlusRecovered.text = "###.###"
            binding.tvDeath.text = "###.###"
            binding.tvPlusDeath.text = "###.###"
            lineChart.data = LineData()
            lineChart.invalidate()

            homeViewModel.getSumPatient()
            homeViewModel.getChartCovid()
            swipeRefresh.isRefreshing = false
        }

        homeViewModel.sumPatient.observe(viewLifecycleOwner) {
            binding.tvConfirm.text = formatNumber(it.confirmed)
            binding.tvPlusConfirm.text = formatNumber(it.plusConfirmed)

            binding.tvCuring.text = formatNumber(it.confirmed - it.recovered - it.death)
            binding.tvPlusCuring.text =
                formatNumber(it.plusConfirmed - it.plusRecovered - it.plusDeath)

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
        lineChart.axisLeft.axisMaximum = 45000f
        lineChart.extraBottomOffset = 10f
        lineChart.data = LineData()
        lineChart.axisLeft.textSize = 12f
        lineChart.xAxis.textSize = 12f
        lineChart.legend.textSize = 12f

        homeViewModel.chartCovidList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                lineChart.marker = CustomMarkerView(context, R.layout.custom_marker_view)

                val chartCovidList = it.subList(it.size - 7, it.size)

                // Số ca nhiễm
                val confirmedLineList = mutableListOf<Entry>()
                chartCovidList.forEachIndexed { index, chartCovidModel ->
                    confirmedLineList.add(
                        Entry(
                            index.toFloat(),
                            chartCovidModel.confirmed.toFloat()
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

                // Đã khỏi bệnh
                val recoveredLineList = mutableListOf<Entry>()
                chartCovidList.forEachIndexed { index, chartCovidModel ->
                    recoveredLineList.add(
                        Entry(
                            index.toFloat(),
                            chartCovidModel.recovered.toFloat()
                        )
                    )
                }
                val recoveredLineDataSet = LineDataSet(recoveredLineList, "Đã khỏi")
                recoveredLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                recoveredLineDataSet.setDrawValues(false)
                context?.let { ContextCompat.getColor(it, R.color.color_recovered) }
                    ?.let {
                        recoveredLineDataSet.color = it
                        recoveredLineDataSet.setCircleColor(it)
                    }

                // Tử vong
                val deathLineList = mutableListOf<Entry>()
                chartCovidList.forEachIndexed { index, chartCovidModel ->
                    deathLineList.add(
                        Entry(
                            index.toFloat(),
                            chartCovidModel.death.toFloat()
                        )
                    )
                }
                val deathLineDataSet = LineDataSet(deathLineList, "Tử vong")
                deathLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                deathLineDataSet.setDrawValues(false)
                context?.let { ContextCompat.getColor(it, R.color.color_death) }
                    ?.let {
                        deathLineDataSet.color = it
                        deathLineDataSet.setCircleColor(it)
                    }

                lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return chartCovidList[value.toInt()].date
                    }
                }
                lineChart.data = LineData(
                    mutableListOf<ILineDataSet>(
                        confirmedLineDataSet,
                        recoveredLineDataSet,
                        deathLineDataSet
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

