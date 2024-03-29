package com.sd.tree_test.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.sd.tree_test.dao.NodeDao
import com.sd.tree_test.dto.NodeItem
import com.sd.tree_test.entity.NodeItemEntity
import com.sd.tree_test.entity.toDto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val nodeDao: NodeDao
) : Repository {

    private val prefs = context.getSharedPreferences("itemTree", Context.MODE_PRIVATE)
    private val idKey = "id"

    override fun getIdCurrentParent(): Long = prefs.getLong(idKey, 0L)

    override fun setParentToSharedpref(id: Long) {
        with(prefs.edit()) {
            putLong(idKey, id)
            apply()
        }
    }

    override fun getAllChildren(id: Long) = nodeDao.getAllChildren(id).toDto()

    override fun getItemById(id: Long) = nodeDao.getItemById(id)?.toDto()

    override fun getMaxId() = nodeDao.getMaxId()

    override fun add(nodeItem: NodeItem) = nodeDao.insert(NodeItemEntity.fromDto(nodeItem))

    override fun delete(id: Long) = nodeDao.deleteItemById(id)

    override fun deleteItemByIdParent(idParent: Long) = nodeDao.deleteItemByIdParent(idParent)

    override fun getAll() = nodeDao.getAll().toDto().toMutableList()

}