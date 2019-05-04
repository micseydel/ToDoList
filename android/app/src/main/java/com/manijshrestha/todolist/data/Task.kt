package com.manijshrestha.todolist.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.time.OffsetDateTime

// TODO: add created_at and modified_at timestamps:
//   https://stackoverflow.com/questions/48922384/how-to-implement-created-at-and-updated-at-column-using-room-persistence-orm-too
@Entity(tableName = "task")
data class Task(
        @ColumnInfo(name = "completed_flag") var completed: Boolean = false,
        @ColumnInfo(name = "task_description") var description: String,
        @ColumnInfo(name = "snoozed_to") var snoozedTo: Long? = null
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}