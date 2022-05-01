package com.quang.vncovid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.quang.vncovid.R
import com.quang.vncovid.activity.DetailReportActivity
import com.quang.vncovid.data.model.ReportModel
import com.quang.vncovid.databinding.ItemReportBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


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
            val date = item.date.toDate()

            binding.tvDay.text = SimpleDateFormat("dd", Locale.US).format(date)
            binding.tvMonthYear.text = SimpleDateFormat("MM/yyyy", Locale.US).format(date)

            if (item.patientHadCovid || item.foreignerHadCovid || item.patientHadSymptom) {
                binding.tvCommunicate.text = context.getString(R.string.text_yes)
                binding.tvCommunicate.setTextColor(ContextCompat.getColor(context, R.color.yes_color))
            } else {
                binding.tvCommunicate.text = context.getString(R.string.text_no)
                binding.tvCommunicate.setTextColor(ContextCompat.getColor(context, R.color.no_color))
            }

            if (item.haveSymptom) {
                binding.tvSymptom.text = context.getString(R.string.text_yes)
                binding.tvSymptom.setTextColor(ContextCompat.getColor(context, R.color.yes_color))
            } else {
                binding.tvSymptom.text = context.getString(R.string.text_no)
                binding.tvSymptom.setTextColor(ContextCompat.getColor(context, R.color.no_color))
            }

            if (item.throughPlace) {
                binding.tvPlace.text = context.getString(R.string.text_yes)
                binding.tvPlace.setTextColor(ContextCompat.getColor(context, R.color.yes_color))
            } else {
                binding.tvPlace.text = context.getString(R.string.text_no)
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