package com.example.musicapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.musicapp.adapters.StatusAdapter
import kotlinx.android.synthetic.main.activity_elements.progressBar
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {
    lateinit var adapter: StatusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        handler.postDelayed( {
            adapter = StatusAdapter(this@StatusActivity) //make adapter for filtering with genre (field4)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            listView6.layoutManager = layoutManager
            listView6.adapter = adapter
            progressBar.visibility = View.GONE
        }, 1000)
    }
}