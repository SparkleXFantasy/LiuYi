package com.bytedance.sjtu.liuyi

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.*

class IdeaItemCreationActivity : AppCompatActivity() {
    private val dbHelper = IdeaItemDBHelper(this, "idea.db", 1)
    private var db : SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idea_item_creation)
        bindDatabase()
        setViewOnClickListener()
    }

    private fun bindDatabase() {
        db = dbHelper.writableDatabase
    }

    private fun setViewOnClickListener() {
        val backView = findViewById<LinearLayout>(R.id.ll_back_view)
        backView.setOnClickListener {
            setResult(IdeaActivity.IdeaItemCreationFailCode)
            finish()
        }
        val creationView = findViewById<Button>(R.id.btn_creation)
        creationView.setOnClickListener {
            val ideaItemCreated = createIdeaItem()
            if (ideaItemCreated) {
                setResult(IdeaActivity.IdeaItemCreationSuccessCode)
                finish()
            }
        }
    }

    private fun createIdeaItem() : Boolean {
        val ideaItem = packIdeaItemData()
        if (checkValidIdeaItem(ideaItem)) {
            dbHelper.insertIdeaItem(db, ideaItem)
            return true
        }
        else {
            Toast.makeText(this, "分享一些感悟吧", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    private fun packIdeaItemData() : IdeaItem {
        val tv = findViewById<TextView>(R.id.et_idea_item_creation)
        val text = tv.text.toString()
        val tagFormatter = SimpleDateFormat("yyyy-mm-dd-hh-mm-ss")
        val formattedTag = tagFormatter.format(Date())
        val formattedDate = formattedTag.substring(0, 10)
        val imgPath = ""
        val videoPath = ""
        return IdeaItem(formattedDate, text, imgPath, videoPath, formattedTag)
    }

    private fun checkValidIdeaItem(ideaItem: IdeaItem) : Boolean {
        return !(ideaItem.text.isBlank() && ideaItem.img.isBlank() && ideaItem.video.isBlank())
    }
}