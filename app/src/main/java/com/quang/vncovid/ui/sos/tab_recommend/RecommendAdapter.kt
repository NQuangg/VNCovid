package com.quang.vncovid.ui.sos.tab_recommend

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginTop
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quang.vncovid.activity.PhotoActivity
import com.quang.vncovid.data.model.RecommendationModel
import com.quang.vncovid.databinding.ItemRecommendBinding

class RecommendAdapter :
    ListAdapter<RecommendationModel, RecommendAdapter.ViewHolder>(RecommendModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecommendationModel) {
            val context = binding.root.context

            binding.tvRecommend.text = item.name
            binding.cvRecommend.setOnClickListener {
                val intent = Intent(context, PhotoActivity::class.java)
                intent.putStringArrayListExtra("list_image", item.listImageUrl)
                context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecommendBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class RecommendModelDiffCallback : DiffUtil.ItemCallback<RecommendationModel>() {

    override fun areItemsTheSame(
        oldItem: RecommendationModel,
        newItem: RecommendationModel
    ): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(
        oldItem: RecommendationModel,
        newItem: RecommendationModel
    ): Boolean {
        return oldItem == newItem
    }
}