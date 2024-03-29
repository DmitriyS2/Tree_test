package com.sd.tree_test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sd.tree_test.dao.NodeDao
import com.sd.tree_test.entity.NodeItemEntity

@Database(
    entities = [NodeItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}