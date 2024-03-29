package com.sd.tree_test.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.sd.tree_test.R
import com.sd.tree_test.adapter.Listener
import com.sd.tree_test.adapter.NodeAdapter
import com.sd.tree_test.databinding.FragmentMainBinding
import com.sd.tree_test.dto.NodeItem
import com.sd.tree_test.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.green)
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        val adapter = NodeAdapter(object : Listener {
            override fun goToChildren(nodeItem: NodeItem) {
                viewModel.changeCurrentParent(nodeItem.id)
            }

            override fun delete(nodeItem: NodeItem) {
                viewModel.deleteParentFromChildren(nodeItem)
            }

        })

        binding.rwChildren.adapter = adapter

        viewModel.dataChildren.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.currentParent.observe(viewLifecycleOwner) {
            binding.nameParent.text = it.name
        }

        viewModel.maxId.observe(viewLifecycleOwner) {
            viewModel.doHash()
        }

        binding.cwNameParent.setOnClickListener {
            if (viewModel.currentParent.value?.id == 1L) {
                Toast.makeText(activity, getString(R.string.you_cant_do_it), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_parent)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.toParent -> {
                            viewModel.changeCurrentParent(
                                viewModel.currentParent.value?.idParent ?: 0L
                            )
                            true
                        }

                        R.id.removeParent -> {
                            viewModel.deleteParent()
                            true
                        }

                        else -> false
                    }
                }
            }.show()
        }

        binding.fab.setOnClickListener {
            viewModel.addChild()
        }

        return binding.root
    }
}