package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.domain.BookRepoImpl
import com.google.android.material.textfield.TextInputEditText

class DeleteActivity : AppCompatActivity() {
    var repo = BookRepoImpl()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        val deleteButton: Button = findViewById(R.id.delete_button)

        val input_name: TextInputEditText = findViewById(R.id.input_name)

        repo = getIntent().extras?.get("Repo") as BookRepoImpl

        System.out.println("AAAAAAAAAAAAAAA" + repo)

        deleteButton.setOnClickListener{
            val input_name_text = input_name.text.toString()
            repo.deteleBookByName(input_name_text)
            System.out.println("BBBBBBBBBBBBBBB"+repo.booklist.toString())
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        System.out.println("pressed back")
        val intent = Intent(this, SeeAllActivity::class.java)
        intent.putExtra("Repo", repo);
        startActivity(intent)
    }
}