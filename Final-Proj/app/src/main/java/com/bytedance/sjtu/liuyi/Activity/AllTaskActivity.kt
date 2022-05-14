package com.bytedance.sjtu.liuyi.Activity

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.sjtu.liuyi.Adapter.AllTaskAdapter
import com.bytedance.sjtu.liuyi.TodoListDBHelper
import com.bytedance.sjtu.liuyi.DataClass.TaskElement
import com.bytedance.sjtu.liuyi.R

class AllTaskActivity : AppCompatActivity(){
    private lateinit var db : SQLiteDatabase
    private lateinit var singleTaskAdapter : AllTaskAdapter
    private var taskList = mutableListOf<TaskElement>()
    private lateinit var all_task_toolbar : androidx.appcompat.widget.Toolbar
    private val dbHelper : TodoListDBHelper = TodoListDBHelper(this, TODOLIST_DB_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_all_task)
        val date = intent.extras?.getString("task_date").toString()
        db = dbHelper.openDB()
        Log.d("##########", "Entered Successful")

        // 添加顶部返回按钮
        all_task_toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.all_task_toolbar)
        all_task_toolbar.setTitle("任务列表")
        all_task_toolbar.setNavigationIcon(R.drawable.to_left)
        setSupportActionBar(all_task_toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        // 设置左上角返回箭头
        all_task_toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "已返回", Toast.LENGTH_SHORT).show()
            finish()
        }

        val allTaskRecyclerView = findViewById<RecyclerView>(R.id.all_task_recyclerview)
        val allTaskLinearLayoutManager = LinearLayoutManager(this)
        allTaskLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        allTaskRecyclerView.layoutManager = allTaskLinearLayoutManager
        singleTaskAdapter = AllTaskAdapter(this)

        getDataFromDB(date)
        singleTaskAdapter.updateTaskList(taskList)
        allTaskRecyclerView.adapter = singleTaskAdapter
    }

    private fun getDataFromDB(date : String) {
        taskList.clear()
        val cursor = (db?: dbHelper.writableDatabase).query("task", null, "task_date = ?", arrayOf(date), null, null, null, null)
        while (cursor.moveToNext()) {
            val task_title : String? = cursor.getString(cursor.getColumnIndexOrThrow("task_title"))
            val task_status : String? = cursor.getString(cursor.getColumnIndexOrThrow("task_status"))
            val task_detail : String? = cursor.getString(cursor.getColumnIndexOrThrow("task_detail"))
            val task_duration : String? = cursor.getString(cursor.getColumnIndexOrThrow("task_duration"))
            val task_tag : String? = cursor.getString(cursor.getColumnIndexOrThrow("task_tag"))
            val task_date : String? = cursor.getString(cursor.getColumnIndexOrThrow("task_date"))
            val task : TaskElement = TaskElement(task_title, task_detail, task_tag, task_status, task_duration, task_date)
            taskList.add(task)
        }
        cursor.close()
    }
}