package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.domain.Book
import com.example.myapplication.domain.BookRepoImpl
import com.google.android.material.textfield.TextInputEditText

class AddActivity : AppCompatActivity() {
    var repo = BookRepoImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val addButton: Button = findViewById(R.id.add_button)

        val input_name: TextInputEditText = findViewById(R.id.input_name)
        val input_landed: TextInputEditText = findViewById(R.id.input_landed)
        val input_author: TextInputEditText = findViewById(R.id.author)
        val input_state: TextInputEditText = findViewById(R.id.state)
        val input_description: TextInputEditText = findViewById(R.id.description)


        repo = getIntent().extras?.get("Repo") as BookRepoImpl
        System.out.println("AAAAAAAAAAAAAAA" + repo)
        System.out.println(repo.javaClass)


//        val input_name_text = input_name.text.toString()
//        val input_landed_text = input_landed.text.toString()


        addButton.setOnClickListener{
            val input_name_text = input_name.text.toString()
            val input_landed_text = input_landed.text.toString()
            val input_author_text = input_author.text.toString()
            val input_state_text = input_state.text.toString()
            val input_description_text = input_description.text.toString()
            repo.addBook(Book(input_name_text,input_landed_text,input_description_text,input_state_text,input_author_text))

            System.out.println("BBBBBBBBBBBBBBB"+repo.booklist.toString())
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        System.out.println("pressed back")
        val intent = Intent(this, SeeAllActivity::class.java)
        System.out.println(repo.booklist)
        intent.putExtra("Repo", repo);
        startActivity(intent)
    }
}