package com.bytedance.sjtu.liuyi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class CreateReport : AppCompatActivity() {
    var curm: Int?=null
    var cury: Int?=null
    var curd: Int ?=null
    var mTitle:  TextView? = null
//    TodoContent
    var TodoUseDaySum: Int?=null
    var TodoSum: Int?=null
    var TodoCompleteSum: Int?=null
    var DayTodoMax: Int?=null
    var DayTodoMaxDay: Int?=null
    var DayCompleteMax: Int?=null
    var DayCompleteMaxDay: Int?=null
    var DayFailMax:Int?=null
    var DayFailMaxDay: Int?=null
//    MemoryContent
    var IdeaSum:Int?=null
    var DayIdeaMax: Int?=null
    var DayIdeaMaxDay: Int?=null
//    UI
    var mTodoSum: TextView?=null
    var mTodoMax: TextView?=null
    var mTodocMax: TextView?=null
    var mTodofMax: TextView?=null
    var mIdeaSum: TextView?=null
    var mIdeaMax: TextView?=null
    var museSum: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report)
        initData()
        initView()
    }
    fun initData(){
        cury=intent.extras?.getInt("year")
        curm=intent.extras?.getInt("month")
        curd=intent.extras?.getInt("day")
        mTitle=findViewById(R.id.report_title)
        museSum=findViewById(R.id.report_usesum)
        mIdeaMax=findViewById(R.id.report_ideamax)
        mIdeaSum=findViewById(R.id.report_ideasum)
        mTodoMax=findViewById(R.id.report_todomax)
        mTodoSum=findViewById(R.id.report_todosum)
        mTodocMax=findViewById(R.id.report_todocmax)
        mTodofMax=findViewById(R.id.report_todofmax)

        TodoUseDaySum = 30
        TodoSum=100
        TodoCompleteSum=90
        DayTodoMax=5
        DayTodoMaxDay = 10
        DayCompleteMax = 4
        DayCompleteMaxDay = 4
        DayFailMax=2
        DayFailMaxDay=6
        IdeaSum=20
        DayIdeaMax=5
        DayIdeaMaxDay=10

    }
    fun initView(){
        mTitle!!.text = String.format("请查收来自留忆的%s月报告", curm)
        museSum!!.text = String.format("你本月总计使用留忆%s天",TodoUseDaySum)
        mTodoSum!!.text = String.format("你这个月总共建立了%s个待办事项\n完成了其中的%s个",
        TodoSum,TodoCompleteSum)
        mTodoMax!!.text=String.format("你在%s月%s日建立了最多的待办事项\n竟高达%s个",curm,
        DayTodoMaxDay,DayTodoMax)
        mTodocMax!!.text=String.format("你在%s月%s日完成了最多的待办事项\n足足有%s个，你好棒！！",curm,
            DayCompleteMaxDay, DayCompleteMax)
        mTodofMax!!.text=String.format("你在%s月%s日有最多的待办事项未完成\n有%s个，下次加油呀！！",curm,
             DayFailMaxDay,DayFailMax)
        mIdeaSum!!.text=String.format("你这个月总共建立了%s条回忆感悟",
            IdeaSum)
        mIdeaMax!!.text=String.format("你在%s月%s日写了最多的感悟\n足足有%s条，那天一定很难忘吧",curm,DayIdeaMax,
        DayIdeaMaxDay)
    }
}