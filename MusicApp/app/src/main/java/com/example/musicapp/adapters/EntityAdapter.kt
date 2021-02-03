package com.example.musicapp.adapters

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.musicapp.EditActivity
import com.example.musicapp.R
import com.example.musicapp.local_db.DbManager
import com.example.musicapp.models.Song
import com.example.musicapp.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_view_clerk.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class EntityAdapter(val context: Context) :
    RecyclerView.Adapter<EntityAdapter.ElementViewAdapter>() {

    val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Song> = ArrayList()
    private lateinit var dbManager: DbManager


    init {
        dbManager = DbManager(context)
        refreshElements()
    }


    class ElementViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityAdapter.ElementViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_clerk, parent, false)

        return ElementViewAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementViewAdapter, position: Int) {
        holder.view.title.text = "NAME " + elementsList[position].name // name
        holder.view.album.text = "STATUS " + elementsList[position].status // field3
        holder.view.genre.text = "QUANTITY " + elementsList[position].quantity.toString() // field4
        holder.view.year.text = "ID " + elementsList[position].id.toString() // field5
        holder.view.btnDelete.setOnClickListener { showDeleteDialog(holder, elementsList[position]) }

        holder.view.setOnClickListener {
//            val aux = Intent(context, EditActivity::class.java)
//            Log.d("AAA  put id:" ,elementsList[position].id.toString())
//            aux.putExtra("id", elementsList[position].id.toString()) // name
//            aux.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(aux)
            showDeleteDialog(holder, elementsList[position])
        }
    }

    override fun getItemCount() = elementsList.size

    fun refreshElements() {
        if (checkOnline()) {
            client.getElements()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        elementsList.clear()
                        elementsList.addAll(result)
                        elementsList.sortBy { T-> T.name }
                        for (el in elementsList){
                            val values = ContentValues()
                            values.put("name", el.name) // name
                            values.put("quantity", el.quantity) // field2
                            values.put("price", el.price) // field3
                            values.put("status", el.status) // field4
                            dbManager.insert(values)
//                            Log.d("DBBBB", "inserted")
                        }
//                        elementsList.sortWith(compareBy({ it.status }, { it.name })) // field3, name
                        notifyDataSetChanged()
//                        Log.d("Elements -> ", elementsList.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${JSONObject(body.string()).getString("text")}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            val cursor = dbManager.queryAll()
//            Log.d("AAAA", "initialised")
            elementsList.clear()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val name = cursor.getString(cursor.getColumnIndex("name")) // name
                    val quantity = cursor.getInt(cursor.getColumnIndex("quantity")) // field2
                    val price = cursor.getInt(cursor.getColumnIndex("price")) // field3
                    val status = cursor.getString(cursor.getColumnIndex("status")) // field4
                    // id, name, field2, field3, field4, field5
//                    if(name=="BBB"){
//                        Log.d("AICIIIIII","ai gasit bbb")
//                    }
                    elementsList.add(Song(id = id, name = name, quantity = quantity, status = status, price = price))
                } while (cursor.moveToNext())
            }
            elementsList.sortByDescending { T -> T.name }
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false

    }

    fun addElement(element: Song) {
        if (checkOnline()) {
            client.addElement(element)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
                        Log.d("Element added -> ", element.toString())
                        Toast.makeText(context, "Added element "+element.name, Toast.LENGTH_LONG).show()
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${body.string()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            dbManager.deleteAll()
            val values = ContentValues()
            values.put("name", element.name) // name
            values.put("quantity", element.quantity) // field2
            values.put("status", element.status) // field3
            values.put("price", element.price) // field5
            Log.d("DB", "inserted into db:"+element.name)
            dbManager.insert(values) // insert values into db - offline only (favourites) // insert values into db - offline only (favourites)
            refreshElements()
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    fun editElement(element: Song) {
        Log.d("AAAA",element.id.toString())
        if (checkOnline()) {
            client.updateElement(element.id!!, element)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
                        Log.d("Element edited -> ", element.toString())
                    },
                    { throwable ->
                        if (throwable is HttpException) {
                            val body: ResponseBody = throwable.response().errorBody()!!
                            Toast.makeText(
                                context,
                                "Error: ${body.string()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteElement(element: Song) { // delete
        if (checkOnline()) {
            client.deleteElement(element.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        refreshElements()
                        Log.d("Element deleted -> ", element.toString())
                    },
                    { throwable ->
                        Toast.makeText(context, "Delete error: ${throwable.message}", Toast.LENGTH_LONG).show()
                    }
                )
        } else {
            Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
        }

    }

    private fun showDeleteDialog(holder: ElementViewAdapter, element: Song) { // confirm delete
        val dialogBuilder = AlertDialog.Builder(holder.view.context)
        dialogBuilder.setTitle("Delete")
        dialogBuilder.setMessage("Confirm delete?")
        dialogBuilder.setPositiveButton("Delete") { _, _ ->
            deleteElement(element)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val b = dialogBuilder.create()
        b.show()
    }
}