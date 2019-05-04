package com.manijshrestha.todolist.ui

import com.manijshrestha.todolist.data.Task

interface ToDoPresentation {

    fun showTasks(tasks: MutableList<Task>)

    fun taskAddedAt(position: Int)

    fun scrollTo(position: Int)
}