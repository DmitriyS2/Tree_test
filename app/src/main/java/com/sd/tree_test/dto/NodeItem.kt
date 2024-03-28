package com.sd.tree_test.dto

data class NodeItem(
    val id: Int,
    val name: String = "",
    val children: MutableList<Int> = mutableListOf(),
    val idParent: Int = 0,
    val parents: MutableList<Int> = mutableListOf()
)
