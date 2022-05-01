package com.quang.vncovid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.quang.vncovid.R
import com.quang.vncovid.activity.DetailReportActivity
import com.quang.vncovid.data.model.ReportModel
import com.quang.vncovid.databinding.ItemReportBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class ReportAdapter(val launcher: ActivityResultLauncher<Intent>): ListAdapter<ReportModel, ReportAdapter.ViewHolder>(ReportModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, launcher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReportModel, launcher: ActivityResultLauncher<Intent>) {
            val context = binding.root.context
            val timeStamp = item.timestamp
            val datetime = timeStamp.toDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            binding.tvDay.text = datetime.format(
                DateTimeFormatter.ofPattern("dd"))
            binding.tvMonthYear.text = datetime.format(
                DateTimeFormatter.ofPattern("MM/yyyy"))

            if (item.patientHadCovid || item.foreignerHadCovid || item.patientHadSymptom) {
                binding.tvCommunicate.text = " : Có"
                binding.tvCommunicate.setTextColor(ContextCompat.getColor(context, R.color.yes_color))
            } else {
                binding.tvCommunicate.text = " : Không"
                binding.tvCommunicate.setTextColor(ContextCompat.getColor(context, R.color.no_color))
            }

            if (item.haveSymptom) {
                binding.tvSymptom.text = " : Có"
                binding.tvSymptom.setTextColor(ContextCompat.getColor(context, R.color.yes_color))
            } else {
                binding.tvSymptom.text = " : Không"
                binding.tvSymptom.setTextColor(ContextCompat.getColor(context, R.color.no_color))
            }

            if (item.throughPlace) {
                binding.tvPlace.text = " : Có"
                binding.tvPlace.setTextColor(ContextCompat.getColor(context, R.color.yes_color))
            } else {
                binding.tvPlace.text = " : Không"
                binding.tvPlace.setTextColor(ContextCompat.getColor(context, R.color.no_color))
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailReportActivity::class.java)
                intent.putExtra("report", Gson().toJson(item))
                launcher.launch(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReportBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class ReportModelDiffCallback : DiffUtil.ItemCallback<ReportModel>() {

    override fun areItemsTheSame(oldItem: ReportModel, newItem: ReportModel): Boolean {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: ReportModel, newItem: ReportModel): Boolean {
        return oldItem == newItem
    }
}