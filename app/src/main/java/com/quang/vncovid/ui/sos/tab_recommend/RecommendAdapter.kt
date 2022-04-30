package com.quang.vncovid.ui.sos.tab_recommend

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.quang.vncovid.activity.PhotoActivity
import com.quang.vncovid.data.model.RecommendModel
import com.quang.vncovid.databinding.ItemRecommendBinding
import java.util.ArrayList

class RecommendAdapter :
    ListAdapter<RecommendModel, RecommendAdapter.ViewHolder>(RecommendModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecommendModel) {
            val context = binding.root.context

            binding.tvRecommend.text = item.name
            binding.cvRecommend.setOnClickListener {
                val intent = Intent(context, PhotoActivity::class.java)
                intent.putStringArrayListExtra("list_image", item.imagePath)
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


class RecommendModelDiffCallback : DiffUtil.ItemCallback<RecommendModel>() {

    override fun areItemsTheSame(oldItem: RecommendModel, newItem: RecommendModel): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: RecommendModel, newItem: RecommendModel): Boolean {
        return oldItem == newItem
    }
}