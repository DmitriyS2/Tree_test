package com.sd.tree_test.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sd.tree_test.entity.NodeItemEntity

@Dao
interface NodeDao {

    //дать все item'ы
    @Query("SELECT * FROM NodeItemEntity")
    fun getAll(): List<NodeItemEntity>

    //дать все item'ы детей конкретного родителя
    @Query("SELECT * FROM NodeItemEntity WHERE idParent = :idParent")
    fun getAllChildren(idParent: Long): List<NodeItemEntity>

    //дать item по Id
    @Query("SELECT * FROM NodeItemEntity WHERE id = :id")
    fun getItemById(id: Long): NodeItemEntity?

    //добавить новый item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nodeItemEntity: NodeItemEntity): Long

    //удалить item по id
    @Query("DELETE FROM NodeItemEntity WHERE id = :id")
    fun deleteItemById(id: Long)

    //удалить item по idParent
    @Query("DELETE FROM NodeItemEntity WHERE idParent = :idParent")
    fun deleteItemByIdParent(idParent: Long)

    //дать maxId в БД
    @Query("SELECT MAX(id) FROM NodeItemEntity")
    fun getMaxId(): LiveData<Long>
}