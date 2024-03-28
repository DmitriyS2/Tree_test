package com.sd.tree_test.adapter

import android.annotation.SuppressLint
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
   //     fun like(product: Product)

    }

    class NodeAdapter (private val listener: Listener) :
        ListAdapter<NodeItem, NodeAdapter.NodeHolder>(DiffCallback()) {

        class NodeHolder(item: View, private val listener: Listener) :
            RecyclerView.ViewHolder(item) {

            val binding = NodeItemBinding.bind(item)

            fun bind(nodeItem: NodeItem) = with(binding) {

                cwNode.setOnClickListener {

                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.main_menu)
                        menu.setGroupVisible(R.id.toParent, nodeItem.id!=0)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.up -> {
                              //      onInteractionListener.remove(post)
                                    true
                                }

                                R.id.remove -> {
                            //        onInteractionListener.edit(post)
                                    true
                                }

                                else -> false
                            }
                        }
                    }.show()
                }



//                cardViewItemCatalog.setOnClickListener {
//                    listener.goToProduct(image, product)
//                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.node_item, parent, false)
            return NodeHolder(view, listener)
        }

        override fun onBindViewHolder(holder: NodeHolder, position: Int) {
            val nodeitem = getItem(position)
            holder.bind(nodeitem)
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