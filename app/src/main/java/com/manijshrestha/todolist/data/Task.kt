package com.manijshrestha.todolist.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "task")
data class Task(@ColumnInfo(name = "completed_flag") var completed: Boolean = false,
           @ColumnInfo(name = "task_description") var description: String) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}