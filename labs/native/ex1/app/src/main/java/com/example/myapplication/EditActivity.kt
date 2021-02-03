package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.domain.BookRepoImpl
import com.google.android.material.textfield.TextInputEditText

class EditActivity : AppCompatActivity() {
    var repo = BookRepoImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val editButton:Button = findViewById(R.id.edit_button)
        val delbtn:Button = findViewById(R.id.del_btn)

        val input_name:TextInputEditText = findViewById(R.id.input_name)
        val input_landed:TextInputEditText = findViewById(R.id.input_landed)
        val input_author:TextInputEditText = findViewById(R.id.author)
        val input_state:TextInputEditText = findViewById(R.id.state)
        val input_description:TextInputEditText = findViewById(R.id.description)


        repo = intent.extras?.get("repo") as BookRepoImpl
        var pos = intent.extras?.get("pos") as Int
        System.out.println("AAAAAAAAAAAAAAA" + repo)
        System.out.println(repo.javaClass)


//        val input_name_text = input_name.text.toString()
//        val input_landed_text = input_landed.text.toString()


        editButton.setOnClickListener{
            val input_name_text = input_name.text.toString()
            val input_landed_text = input_landed.text.toString()
            val input_author_text = input_author.text.toString()
            val input_state_text = input_state.text.toString()
            val input_description_text = input_description.text.toString()
            val book_index: Int = repo.getBookByName(input_name_text)
            repo.booklist.get(pos).landed = input_landed_text
            repo.booklist.get(pos).author = input_author_text
            repo.booklist.get(pos).state = input_state_text
            repo.booklist.get(pos).description = input_description_text

            System.out.println("BBBBBBBBBBBBBBB"+repo.booklist.toString())
//            val intent:Intent()
//            intent.putExtra("repo", repo)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        delbtn.setOnClickListener{
            repo.deleteBook(repo.booklist[pos]);
            System.out.println("delete"+repo.booklist.toString())
            val returnIntent = Intent()
            returnIntent.putExtra("repo", repo)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.out.println("pressed back")
        val intent = Intent(this, SeeAllActivity::class.java)
        System.out.println(repo.booklist)
        intent.putExtra("Repo", repo);
//        startActivity(intent)
        finish()
    }

    fun deleteBook(view: View) {}

}