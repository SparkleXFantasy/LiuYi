package com.bytedance.sjtu.liuyi

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.sjtu.liuyi.databinding.ActivityScrollingBinding

class IdeaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var rvAdapter : IdeaViewAdapter
    private lateinit var rvIdea: RecyclerView
    private val dbHelper = IdeaItemDBHelper(this, "idea.db", 1)
    private var db : SQLiteDatabase? = null
    private var databaseTestItem : Boolean = false
    private val ideaItemRequestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            IdeaItemCreationSuccessCode -> {
                rvAdapter.setIdeaList(getIdeaItemList())
            }
            IdeaItemCreationFailCode -> {}
        }
    }
    companion object {
        const val IdeaItemCreationSuccessCode : Int = 1001
        const val IdeaItemCreationFailCode : Int = 1002
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener {
            intent = Intent(this, IdeaItemCreationActivity::class.java)
            ideaItemRequestLauncher.launch(intent)
        }
        setDatabaseTestItem(true)    // if set true, four items will be inserted into database for testing.
        bindDatabase()
        initRecyclerView()
        clearDatabaseItem()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindDatabase() {
        db = dbHelper.writableDatabase
        if (databaseTestItem) {
            initTestIdeaItem()
        }
    }

    private fun setDatabaseTestItem(b: Boolean) {
        databaseTestItem = b
    }

    private fun initTestIdeaItem() {
        val ideaItemList = mutableListOf<IdeaItem>(
            IdeaItem("2022-1-12", "addition and subtraction: knowledge to increase life, trouble will decline; Friendship will increase, resentment to decrease; A heart to heart to decrease, increasing; Increasing confidence to promise to decrease,; ） to increase quantity to jealousy, diminishing. Steps to increase to decrease, alcohol and tobacco.", ".", ".", "2022-1-12-22-43-24"),
            IdeaItem("2022-1-13", "have a heart of spring, ecstatic to in full bloom; Again the sea, the mind can open; Good, play to have womb agile; The eyes have god, the look line to sharp; Arm strength, make moves to the punch; With rhythm, steps are to light.", ".", ".", "2022-1-13-5-12-52"),
            IdeaItem("2022-1-15", "life is a song, sing the life rhythm and melody; Life is a road, extend the footprint of the life and hope; Life is a cup of wine, full of life and mellow sorrow; Life is a mass of linen, interweaving the trouble with life and happy; Life is a picture, and describes the life experience of red, green, blue; Life is a fire, burning vision of life and to dream.", ".", ".", "2022-1-15-2-43-24"),
            IdeaItem("2022-2-13", "The sky is full of black clouds. Flashes of lightning lit up the courts, old houses, and ramshackle porches, and thunder begins to roar overhead. No birds sing any longer, but the leaves of the trees rustle and a wind blows against our faces. A drop of water falls, then another, and then the rain falls on the leaves of the trees and the iron roofs with a noise reminding us of the beating of drums. A brilliant stripe o flight fills the sky. Suddenly there comes a terrible thunderclap overhead, and then the sky is full of roars of thunder.", ".", ".", "2022-2-13-17-12-52")
        )
        for (ideaItem in ideaItemList) {
            dbHelper.insertIdeaItem(db, ideaItem)
        }
    }

    private fun clearDatabaseItem() {
        dbHelper.deleteAllItem(db)
    }

    private fun initRecyclerView() {
        val rv = findViewById<RecyclerView>(R.id.idea_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        val adapter = IdeaViewAdapter()
        adapter.setIdeaList(getIdeaItemList())
        rvAdapter = adapter
        rv.adapter = adapter
        rvIdea = rv
    }

    @SuppressLint("Range")
    private fun getIdeaItemList() : MutableList<IdeaItem> {
        val cursor = (db?: dbHelper.writableDatabase).query("idea", null, null, null, null, null, null, null)
        val ideaItemList = mutableListOf<IdeaItem>()
        if (cursor.moveToFirst()) {
            do {
                ideaItemList.add(IdeaItem(
                    cursor.getString(cursor.getColumnIndex("idea_date")),
                    cursor.getString(cursor.getColumnIndex("idea_text")),
                    cursor.getString(cursor.getColumnIndex("idea_image")),
                    cursor.getString(cursor.getColumnIndex("idea_video")),
                    cursor.getString(cursor.getColumnIndex("idea_tag"))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ideaItemList
    }
}