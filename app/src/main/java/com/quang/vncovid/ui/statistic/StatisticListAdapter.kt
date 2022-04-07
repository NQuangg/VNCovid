package com.quang.vncovid.ui.statistic

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.vncovid.NewsDetailActivity
import com.quang.vncovid.R
import com.quang.vncovid.data.model.NewsModel
import com.quang.vncovid.data.model.ProvinceModel
import com.quang.vncovid.databinding.ItemNewsBinding
import com.quang.vncovid.databinding.ItemStatisticBinding
import com.quang.vncovid.util.formatNumber

class StatisticAdapter :
    ListAdapter<ProvinceModel, StatisticAdapter.ViewHolder>(ProvinceModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemStatisticBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProvinceModel) {
            binding.tvProvince.text = item.title

            binding.tvOnevaccinepercent.text = "${item.onevaccinepercent}%"
            binding.pbOnevaccinepercent.progress = item.onevaccinepercent.toInt()

            binding.tvDonevaccinepercent.text = "${item.donevaccinepercent}%"
            binding.pbDonevaccinepercent.progress = item.donevaccinepercent.toInt()

            binding.tvConfirmed.text = formatNumber(item.confirmed)
            binding.tvIncconfirmed.text = formatNumber(item.incconfirmed)
            binding.tvDeath.text = formatNumber(item.deaths)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStatisticBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class ProvinceModelDiffCallback : DiffUtil.ItemCallback<ProvinceModel>() {

    override fun areItemsTheSame(oldItem: ProvinceModel, newItem: ProvinceModel): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: ProvinceModel, newItem: ProvinceModel): Boolean {
        return oldItem == newItem
    }
}