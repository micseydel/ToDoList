package com.manijshrestha.todolist.data


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

@Dao
interface TaskDao {

    @Query("select * from task")
    fun getAllTasks(): Flowable<List<Task>>

    @Query("select * from task where snoozed_to is null or snoozed_to < :currentTime")
    fun getAllUnsnoozedTasks(currentTime: Long = System.currentTimeMillis()): Flowable<List<Task>>

    @Query("select * from task where snoozed_to >= :currentTime")
    fun getAllSnoozedTasks(currentTime: Long = System.currentTimeMillis()): Flowable<List<Task>>

    @Query("select * from task where id = :id")
    fun findTaskById(id: Long): Task

    @Insert(onConflict = REPLACE)
    fun insertTask(task: Task)

    @Update(onConflict = REPLACE)
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}