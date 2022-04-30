package com.quang.vncovid.ui.news

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quang.vncovid.activity.NewsDetailActivity
import com.quang.vncovid.data.model.NewsModel
import com.quang.vncovid.databinding.ItemNewsBinding

class NewsAdapter :
    ListAdapter<NewsModel, NewsAdapter.ViewHolder>(NewsModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsModel) {
            val context = binding.root.context

            Glide.with(context)
                .load(item.imageUrl).into(binding.imageView)
            binding.tvTitle.text = item.title
            binding.tvDescription.text = Html.fromHtml(item.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

            binding.root.setOnClickListener {
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra("news_url", item.url)
                context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class NewsModelDiffCallback : DiffUtil.ItemCallback<NewsModel>() {

    override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem == newItem
    }
}