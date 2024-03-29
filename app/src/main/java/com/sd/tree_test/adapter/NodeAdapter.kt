package com.sd.tree_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sd.tree_test.R
import com.sd.tree_test.databinding.NodeItemBinding
import com.sd.tree_test.dto.NodeItem

interface Listener {
    fun goToChildren(nodeItem: NodeItem)
    fun delete(nodeItem: NodeItem)
}

class NodeAdapter(private val listener: Listener) :
    ListAdapter<NodeItem, NodeAdapter.NodeHolder>(DiffCallback()) {

    class NodeHolder(item: View, private val listener: Listener) :
        RecyclerView.ViewHolder(item) {

        private val binding = NodeItemBinding.bind(item)

        fun bind(nodeItem: NodeItem) = with(binding) {

            binding.nameChild.text = nodeItem.name

            cwNode.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_item)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.toChildren -> {
                                listener.goToChildren(nodeItem)
                                true
                            }

                            R.id.removeItem -> {
                                listener.delete(nodeItem)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.node_item, parent, false)
        return NodeHolder(view, listener)
    }

    override fun onBindViewHolder(holder: NodeHolder, position: Int) {
        val nodeItem = getItem(position)
        holder.bind(nodeItem)
    }


    class DiffCallback : DiffUtil.ItemCallback<NodeItem>() {
        override fun areItemsTheSame(oldItem: NodeItem, newItem: NodeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NodeItem, newItem: NodeItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: NodeItem, newItem: NodeItem): Any =
            Payload()
    }

    data class Payload(
        val id: Int? = null
    )
}