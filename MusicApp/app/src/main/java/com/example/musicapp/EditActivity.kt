package com.example.musicapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.musicapp.adapters.EntityAdapter
import com.example.musicapp.models.Song
import kotlinx.android.synthetic.main.activity_edit.albumText
import kotlinx.android.synthetic.main.activity_edit.descriptionText
import kotlinx.android.synthetic.main.activity_edit.genreText
import kotlinx.android.synthetic.main.activity_edit.progressBar
import kotlinx.android.synthetic.main.activity_edit.sendButton
import kotlinx.android.synthetic.main.activity_edit.titleText
import kotlinx.android.synthetic.main.activity_edit.yearText

class EditActivity : AppCompatActivity() {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
//        nameEdit.text = intent.getStringExtra("title")

        id =  intent.getStringExtra("id").toInt()




        sendButton.setOnClickListener {
            //val dbManager = DbManager(this)
            val elementAdapter = EntityAdapter(this)

            val values = ContentValues()



//            val item = Song(1,"","")
//            item.name = titleText.text.toString() // name
//            item.quantity = descriptionText.text.toString() // field2
//            item.status =  albumText.text.toString() // field3
//            item.genre = genreText.text.toString() // field4
//            item.price = yearText.text.toString().toInt() // field5
//            progressBar.visibility = View.VISIBLE
//            item.id = id

//            val handler = Handler()
//            handler.postDelayed( {
//                elementAdapter.editElement(item)
//                elementAdapter.refreshElements()
//                progressBar.visibility = View.GONE
//                finish()
//            }, 1000)



        }



}
}