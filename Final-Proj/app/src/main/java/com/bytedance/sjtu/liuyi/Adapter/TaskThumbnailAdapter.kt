package com.bytedance.sjtu.liuyi.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.sjtu.liuyi.MainActivity
import com.bytedance.sjtu.liuyi.Activity.TodoTaskEditActivity
import com.bytedance.sjtu.liuyi.DataClass.TaskElement
import com.bytedance.sjtu.liuyi.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import android.content.Intent
import android.util.Log
import com.bytedance.sjtu.liuyi.Activity.DoneTaskShowActivity
import com.bytedance.sjtu.liuyi.Activity.TaskStartActivity
import org.w3c.dom.Text

/**
 * List: 有序接口, 只能读取, 不能更改元素;
 * MutableList: 有序接口, 可以读写与更改, 删除, 增加元素.
 */

class TaskThumbnailAdapter(activity: MainActivity) : RecyclerView.Adapter<TaskThumbnailAdapter.TaskElementViewHolder>() {
    private var taskList = mutableListOf<TaskElement>()
    private val main_activity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskElementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_tumbnail_recyclerview_entry, parent, false)       // 内容横向铺满
        val viewHolder = TaskElementViewHolder(view)

        var taskStatusCheckbox = view.findViewById<CheckBox>(R.id.task_status_checkbox)

        // todo: 添加对 checkbox 的点击监听事件
//        taskStatusCheckbox.setOnClickListener {
//            main_activity.changeTaskStatus(viewHolder.task_date.text.toString().substring(12), taskStatusCheckbox)
//        }

        // task item 长按跳转到编辑页, 仅未完成事件可编辑，已完成事件仅可查看
        viewHolder.itemView.setOnLongClickListener {
            if (taskStatusCheckbox.isChecked) {
                val intent = Intent(main_activity, DoneTaskShowActivity::class.java)
                intent.putExtra("task_tag", viewHolder.task_entry_date.text)
                main_activity.startActivity(intent)
                false
            }
            else {
                val intent = Intent(main_activity, TodoTaskEditActivity::class.java)
                intent.putExtra("task_tag", viewHolder.task_entry_date.text)
                main_activity.startActivity(intent)
                false
            }
        }

        // task item 点击跳转到任务启动页，仅未完成事件可启动
        viewHolder.itemView.setOnClickListener {
            if (!taskStatusCheckbox.isChecked) {
                val intent = Intent(main_activity, TaskStartActivity::class.java)
                intent.putExtra("task_tag", viewHolder.task_entry_date.text)
                main_activity.startActivity(intent)
            }
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskElementViewHolder, idx: Int) {
        holder.task_entry_date.text = "${taskList[idx].task_tag}"
        holder.task_entry_title.text = "${taskList[idx].task_title}"
        holder.task_entry_status.isChecked = taskList[idx].task_status != "todo"
        holder.task_entry_duration.text = "${taskList[idx].task_duration}"
    }

    override fun getItemCount(): Int = taskList.size

    @SuppressLint("SimpleDateFormat")
    val myComparator : Comparator<TaskElement> = Comparator { task_1, task_2 ->
        var result : Int
        if (task_1.task_status == task_2.task_status) {
            val dateformatter : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd-hh-mm-ss")
            val date_1 : Date = dateformatter.parse(task_1.task_date)
            val date_2 : Date = dateformatter.parse(task_2.task_date)
            if (date_1.before(date_2)) result = -1
            else result = 1
        } else {
            if (task_1.task_status == "todo") result =  -1
            else result = 1
        }
        return@Comparator result
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTaskList(myList : List<TaskElement>) {
        taskList.clear()
        taskList.addAll(myList)
        taskList.sortWith(myComparator)
        Log.d("############", taskList.toString())
        notifyDataSetChanged()              // 当数据发生变化时，更新 view
    }

    class TaskElementViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task_entry_date : TextView = view.findViewById<TextView>(R.id.task_entry_date)
        val task_entry_title: TextView = view.findViewById<TextView>(R.id.task_entry_title)
        val task_entry_status: CheckBox = view.findViewById<CheckBox>(R.id.task_status_checkbox)
        val task_entry_duration : TextView = view.findViewById<TextView>(R.id.task_entry_duration)
    }
}