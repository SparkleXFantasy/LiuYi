package com.bytedance.sjtu.liuyi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
            IdeaItemCreationActivity.IdeaItemCreationSuccessCode -> {
                rvAdapter.setIdeaList(getIdeaItemList())
            }
            IdeaItemCreationActivity.IdeaItemCreationFailCode -> {}
        }
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
        setDatabaseTestItem(false)    // if set true, four items will be inserted into database for testing.
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
        val ideaItemList = mutableListOf(
            IdeaItem("2022-5-10", "Keep simple, keep stupid.", "", "", "2022-5-10-22-43-24"),
            IdeaItem("2022-5-12", "Have a heart of spring, ecstatic to in full bloom.", "", "", "2022-5-12-15-42-52"),
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