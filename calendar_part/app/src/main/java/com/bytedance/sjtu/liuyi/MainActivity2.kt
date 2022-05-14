package com.bytedance.sjtu.liuyi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    var curm:Int?=null
    var cury:Int?=null
    var curd:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        cury=intent.extras?.getInt("year")
        curm=intent.extras?.getInt("month")
        curd=intent.extras?.getInt("day")
        Toast.makeText(this, String.format("%s : %s : %s", cury, curm, curd), Toast.LENGTH_SHORT).show()
    }
}