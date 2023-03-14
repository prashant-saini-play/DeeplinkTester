package com.app.deeplinktester.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.deeplinktester.R
import com.app.deeplinktester.databinding.RecyclerviewItemBinding
import com.app.deeplinktester.room.model.DeepLinkData


class DeepLinkListAdapter(private val actionListener: ActionListener) :
    ListAdapter<DeepLinkData, DeepLinkListAdapter.DeepLinkViewHolder>(DeepLinkComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeepLinkViewHolder {
        val holder = DeepLinkViewHolder.create(parent)
        setUpClickListener(holder)
        return holder
    }

    private fun setUpClickListener(holder: DeepLinkViewHolder) {
        holder.getBinding().playButton.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                actionListener.onPlayButtonClick(holder.getBinding().textView.text.toString())
            }
        }

        holder.getBinding().deleteButton.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                actionListener.onDeleteButtonClick(holder.getBinding().textView.text.toString())
            }
        }

        holder.getBinding().copyButton.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                actionListener.onCopyButtonClick(holder.getBinding().textView.text.toString())
            }
        }
    }


    override fun onBindViewHolder(holder: DeepLinkViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.deeplink)
    }

    class DeepLinkViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val deepLinkItemView: TextView = binding.textView

        fun bind(text: String?) {
            deepLinkItemView.text = text
        }

        fun getBinding() : RecyclerviewItemBinding {
            return binding
        }

        companion object {
            fun create(parent: ViewGroup): DeepLinkViewHolder {
                val view: RecyclerviewItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recyclerview_item, parent, false)
                return DeepLinkViewHolder(view)
            }
        }
    }

    class DeepLinkComparator : DiffUtil.ItemCallback<DeepLinkData>() {
        override fun areItemsTheSame(oldItem: DeepLinkData, newItem: DeepLinkData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DeepLinkData, newItem: DeepLinkData): Boolean {
            return oldItem.deeplink == newItem.deeplink
        }
    }

    interface ActionListener {
        fun onPlayButtonClick(deeplink: String)
        fun onDeleteButtonClick(deeplink: String)
        fun onCopyButtonClick(deeplink: String)
    }
}