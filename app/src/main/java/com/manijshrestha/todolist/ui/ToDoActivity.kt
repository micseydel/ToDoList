package com.manijshrestha.todolist.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import com.manijshrestha.todolist.R
import com.manijshrestha.todolist.data.Task
import com.manijshrestha.todolist.data.TaskDao
import dagger.android.AndroidInjection
import javax.inject.Inject

class ToDoActivity : AppCompatActivity(), ToDoPresentation {

    @Inject lateinit var presenter: ToDoPresenter

    @Inject lateinit var taskDao: TaskDao

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        val taskET = findViewById<EditText>(R.id.task_et)
        taskET?.setOnEditorActionListener { view, actionId, event ->
            if ((event?.keyCode == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                addTask(taskET)
            }

            // true here means we dismiss the keyboard
            true
        }

        val addBtn = findViewById<Button>(R.id.add_btn)
        addBtn?.setOnClickListener {
            addTask(taskET)
        }

        recyclerView = findViewById(R.id.tasks_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        presenter.onCreate(this)
    }

    private fun addTask(taskET: EditText?) {
        presenter.addNewTask(taskET?.text.toString())
        taskET?.text?.clear()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showTasks(tasks: MutableList<Task>) {
        recyclerView.adapter = TaskAdapter(tasks, taskDao, this)
    }

    override fun taskAddedAt(position: Int) {
        recyclerView.adapter?.notifyItemInserted(position)
    }

    override fun scrollTo(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }
}
