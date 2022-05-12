package com.quang.vncovid.main_ui.sos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quang.vncovid.data.model.ContactModel
import com.quang.vncovid.databinding.ItemContactBinding

class ContactAdapter(private val onClickItemListener: OnClickItemListener) :
    ListAdapter<ContactModel, ContactAdapter.ViewHolder>(ContactModelDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickItemListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactModel, onClickItemListener: OnClickItemListener) {
            binding.tvName.text = item.name
            binding.tvPhoneNumber.text = item.phoneNumber

            binding.cvPhone.setOnClickListener {
                onClickItemListener.onClickItem(item.phoneNumber)
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

    interface OnClickItemListener {
        public fun onClickItem(phoneNumber: String)
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