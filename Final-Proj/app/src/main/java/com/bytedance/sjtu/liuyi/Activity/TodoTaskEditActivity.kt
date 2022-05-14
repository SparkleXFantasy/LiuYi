package com.bytedance.sjtu.liuyi.Activity

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.bytedance.sjtu.liuyi.DBHelper
import com.bytedance.sjtu.liuyi.R
/*
 * 长按进入未完成任务的编辑页面 （已完成任务不可编辑）
 */
class TodoTaskEditActivity : AppCompatActivity() {
    private lateinit var task_title_edittext: EditText
    private lateinit var task_detail_edittext: EditText
    private lateinit var task_edit_toolbar : androidx.appcompat.widget.Toolbar
    private lateinit var save_task_button : Button
    private val dbHelper = DBHelper(this, "ToDoList_v9.db")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_task_edit)
        val db = dbHelper.writableDatabase
        val old_task_tag = intent.extras?.getString("task_date").toString()

        // 如果是已经之前已经创建过的任务，当用户进入该任务编辑页面时，应该显示用户之前输入的内容
        val task_exist = showContent(old_task_tag)

        task_edit_toolbar = findViewById(R.id.todo_task_edit_bar)
        // 添加保存按钮
        task_edit_toolbar.inflateMenu(R.menu.task_edit_menu)
        save_task_button = findViewById(R.id.save_task)
        setSupportActionBar(task_edit_toolbar)
        // 设置左上角返回箭头
        task_edit_toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "已取消", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 保存按钮点击事件
        save_task_button.setOnClickListener {
            task_title_edittext = findViewById<EditText>(R.id.task_title_edit)
            task_detail_edittext = findViewById<EditText>(R.id.task_detail_edit)

            val dateFormatterForTag = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss")
            val dateFormatterForDate = DateTimeFormatter.ofPattern("yyyy-mm-dd")
            val new_task_tag = dateFormatterForTag.format(LocalDateTime.now())
            val task_date = dateFormatterForDate.format(LocalDateTime.now())
            var task_title = task_title_edittext.text.toString()
            var task_detail = task_detail_edittext.text.toString()

            // 设置默认值
            if (task_title == "") { task_title = "无主题" }
            if (task_detail == "") { task_detail = "无详细描述" }

            val newTask = ContentValues().apply {
                put("task_title", task_title)
                put("task_detail", task_detail)
                put("task_status", "todo")
            }

            if (task_exist) {
                db.update("todolist", newTask, "task_tag = ?", arrayOf(old_task_tag))
            } else {
                newTask.put("task_date", task_date)
                newTask.put("task_tag", new_task_tag)
                newTask.put("task_duration", "0")
                db.insert("todolist", null, newTask)
            }

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
//            Log.d("DataBaseInsert", "————————————————")
//            val intent_1 = Intent()
//            intent_1.setAction("MAIN_FINISH")
//            sendBroadcast(intent_1)                 // 结束 MainActivity
//            finish()
//            Log.d("TaskPageFinished", "#################3")
//            val intent_2 = Intent(this, MainActivity::class.java)
//            startActivity(intent_2)                 // 重新启动 MainActivity
//            Log.d("MainStarted", "##################")
        }
    }

    private fun showContent(task_tag: String): Boolean {
        val myMap = dbHelper.queryTaskInfo(task_tag)
        if (myMap["task_exist"] === "True") {
            task_title_edittext = findViewById<EditText>(R.id.task_title_edit)
            task_title_edittext.setText(myMap["task_title"])

            task_detail_edittext = findViewById<EditText>(R.id.task_detail_edit)
            task_detail_edittext.setText(myMap["task_detail"])
            return true
        } else return false
    }
}