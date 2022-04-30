package com.quang.vncovid.ui.sos

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.databinding.ItemContactBinding

class ContactAdapter :
    ListAdapter<ContactModel, ContactAdapter.ViewHolder>(ContactModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactModel) {
            binding.tvName.text = item.name
            binding.tvPhoneNumber.text = item.phoneNumber

            binding.ibPhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + item.phoneNumber))
                binding.ibPhone.context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemContactBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}


class ContactModelDiffCallback : DiffUtil.ItemCallback<ContactModel>() {

    override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem == newItem
    }
}