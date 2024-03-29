package com.sd.tree_test.dto

data class NodeItem(
    val id: Long = 0L,
    val name: String = "",
    val idParent: Long = 0L,
    val parents: MutableList<Long> = mutableListOf(0L)
)
