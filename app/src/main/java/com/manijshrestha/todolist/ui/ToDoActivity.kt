package com.manijshrestha.todolist.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.manijshrestha.todolist.R
import com.manijshrestha.todolist.data.Task
import dagger.android.AndroidInjection
import javax.inject.Inject

class ToDoActivity : AppCompatActivity(), ToDoPresentation {

    @Inject lateinit var presenter: ToDoPresenter

    private var taskET: EditText? = null
    private var addBtn: Button? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        taskET = findViewById(R.id.task_et)
        addBtn = findViewById(R.id.add_btn)
        recyclerView = findViewById(R.id.tasks_rv)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = TaskAdapter(emptyList())

        addBtn?.setOnClickListener {
            presenter.addNewTask(taskET?.text.toString())
            taskET?.text?.clear()
        }

        presenter.onCreate(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showTasks(tasks: List<Task>) {
        recyclerView?.adapter = TaskAdapter(tasks)
    }

    override fun taskAddedAt(position: Int) {
        recyclerView?.adapter?.notifyItemInserted(position)
    }

    override fun scrollTo(position: Int) {
        recyclerView?.smoothScrollToPosition(position)
    }
}
