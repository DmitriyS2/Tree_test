package com.sd.tree_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sd.tree_test.dto.NodeItem
import com.sd.tree_test.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataChildren = MutableLiveData<List<NodeItem>>()
    val dataChildren: LiveData<List<NodeItem>>
        get() = _dataChildren

    val maxId = repository.getMaxId()

    private val _currentParent = MutableLiveData<NodeItem>()
    val currentParent: LiveData<NodeItem>
        get() = _currentParent

    private var showParent: Long = -1L

    private var hashText = ""

    init {
        loadData()
    }

    private fun loadData() {
        try {
            showParent = repository.getIdCurrentParent()
            if (showParent == 0L) {
                val n = repository.add(NodeItem(name = encrypt(showParent.toString())))
                _currentParent.value = repository.getItemById(n)
                repository.setParentToSharedpref(n)
            } else {
                _currentParent.value = repository.getItemById(showParent)
            }
            _dataChildren.value = repository.getAllChildren(_currentParent.value?.id ?: 0L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun changeCurrentParent(id: Long) {
        try {
            _currentParent.value = repository.getItemById(id)
            _dataChildren.value = repository.getAllChildren(id)
            repository.setParentToSharedpref(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addChild() {
        try {
            val idCurrent = _currentParent.value?.id ?: 0L
            val listParents = _currentParent.value?.parents ?: mutableListOf()
            if (!listParents.contains(idCurrent)) {
                listParents.add(_currentParent.value?.id ?: 0L)
            }
            val nodeItem = NodeItem(
                name = hashText,
                idParent = _currentParent.value?.id ?: 0,
                parents = listParents
            )
            repository.add(nodeItem)
            _dataChildren.value = repository.getAllChildren(_currentParent.value?.id ?: 0L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteParent() {
        try {
            val dataAll = repository.getAll()
            val idN = currentParent.value?.id ?: -1L
            dataAll.forEach {
                if (it.parents.contains(idN)) {
                    repository.delete(it.id)
                }
            }
            repository.delete(idN)
            changeCurrentParent(currentParent.value?.idParent ?: -1L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteParentFromChildren(nodeItem: NodeItem) {
        try {
            val dataAll = repository.getAll()
            val idN = nodeItem.id
            dataAll.forEach {
                if (it.parents.contains(idN)) {
                    repository.delete(it.id)
                }
            }
            repository.delete(idN)
            _dataChildren.value = repository.getAllChildren(_currentParent.value?.id ?: -1L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun encrypt(input: String): String {
        val md = MessageDigest.getInstance("SHA-1")
        val messageDigest = md.digest(input.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashText = no.toString(16)
        while (hashText.length < 32) {
            hashText = "0$hashText"
        }
        return hashText
    }

    fun doHash() {
        hashText = encrypt(maxId.value?.plus(1).toString())
    }
}