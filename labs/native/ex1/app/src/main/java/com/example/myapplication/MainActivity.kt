package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.domain.Book
import com.example.myapplication.domain.BookRepoImpl
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repo = BookRepoImpl()
//        val Book book1 = Book("book1", "yes", "desc1", "old", "auth1")
        val book1 = Book("book1", "yes", "desc1", "old", "auth1")
        val book2 = Book("book2", "yes", "desc1", "old", "auth1")
        val book3 = Book("book3", "yes", "desc1", "old", "auth1")
        val book4 = Book("book4", "yes", "desc1", "old", "auth1")
        val book5 = Book("book5", "yes", "desc1", "old", "auth1")
        System.out.println(repo.addBook(book1))
        repo.addBook(book2)
        repo.addBook(book3)
        repo.addBook(book4)
        repo.addBook(book5)
        System.out.println(book1.toString())
        System.out.println(repo.getBook(book1))
        System.out.println(repo.booklist.toString())
        val my_array = ArrayList<String>()

        for (book in repo.booklist){
            my_array.add(book.name)
        }

        val adapter = ArrayAdapter<String>(
            this,
            R.layout.listview_item,
            my_array
        )


        val listView:ListView = findViewById(R.id.listview_1)
        listView.setAdapter(adapter)
        listView.setOnItemClickListener{parent, view, position, id ->
            val editActivity = Intent(applicationContext, EditActivity::class.java)
            editActivity.putExtra("repo", repo)
            editActivity.putExtra("pos", position + 1)
//            startActivityForResult(editActivity,2);
            var LAUNCH_EDIT = 1
            val i = Intent(this, EditActivity::class.java)
            startActivityForResult(i, 2)
//            repo = onActivityResult(1,i)
        }

//        val edit_btn:Button = findViewById(R.id.edit_btn)
//        edit_btn.setOnClickListener{
//            val intent = Intent(this, EditActivity::class.java)
//            intent.putExtra("Repo", repo);
//            startActivity(intent)
//        }

        val add_btn:Button = findViewById(R.id.add_btn)
        add_btn.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("Repo", repo);
            startActivity(intent)
        }

        val delete_btn:Button = findViewById(R.id.delete_btn)
        delete_btn.setOnClickListener{
            val intent = Intent(this, DeleteActivity::class.java)
            intent.putExtra("Repo", repo);
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Main","On rest reached")
    }
    override fun onPause() {
        super.onPause()
        Log.d("Main","On rest reached")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2){ //edit
            if (resultCode === Activity.RESULT_OK) {
//                return data.getSerializableExtra("repo")
            }
        }
    }
//    fun sendMessage(view: View) {
//        Log.d("Main", "sendMessage: ai apasat butonu")
//    }
}

