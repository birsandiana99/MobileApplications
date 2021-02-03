package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.myapplication.domain.BookRepoImpl

class SeeAllActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all)

        val repo: BookRepoImpl = getIntent().extras?.get("Repo") as BookRepoImpl

        System.out.println("-------------"+repo.booklist)

        val my_array = ArrayList<String>()

        for (book in repo.booklist){
            my_array.add(book.toString())
        }

        val adapter = ArrayAdapter<String>(
            this,
            R.layout.listview_item,
            my_array
        )


        val listView: ListView = findViewById(R.id.listview_1)
        listView.setAdapter(adapter)


        val edit_btn: Button = findViewById(R.id.edit_btn)
        edit_btn.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("Repo", repo);
            startActivity(intent)
        }

        val add_btn: Button = findViewById(R.id.add_btn)
        add_btn.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("Repo", repo);
            startActivity(intent)
        }

        val delete_btn: Button = findViewById(R.id.delete_btn)
        delete_btn.setOnClickListener{
            val intent = Intent(this, DeleteActivity::class.java)
            intent.putExtra("Repo", repo);
            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}