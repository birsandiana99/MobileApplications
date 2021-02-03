package com.example.musicapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.musicapp.R
import com.example.musicapp.models.Song
import com.example.musicapp.networking.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.status_view.view.*
import okhttp3.ResponseBody
import retrofit2.HttpException

class StatusAdapter(val context: Context) :
    RecyclerView.Adapter<StatusAdapter.ElementStatusAdapter>()
{

    val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Song> = ArrayList()

    init {
        refreshElements()
    }


    class ElementStatusAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatusAdapter.ElementStatusAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.status_view, parent, false)

        return ElementStatusAdapter(view)
    }

    override fun onBindViewHolder(holder: ElementStatusAdapter, position: Int) {
        // prepare details and special operation activity
//            Log.d("AAAAAAAAAAAAA",elementsList[position].status.toString())
        holder.view.name.text = "Name " + elementsList[position].name
        holder.view.status.text = "Status " + elementsList[position].status
        holder.view.quantity.text = "Quantity " + elementsList[position].quantity.toString()
        holder.view.price.text = "Price " + elementsList[position].price.toString()

    }


    override fun getItemCount() = elementsList.size

    private fun refreshElements() {
        if (checkOnline()) {
            client.getBought() // /songs/:genre
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        elementsList.clear()
                        elementsList.addAll(result)
                        elementsList.sortByDescending { T-> T.quantity }

                        var elementsList2: ArrayList<Song> = ArrayList()

                        for(i in 0..9){
                            elementsList2.add(elementsList[i])
                        }
                        elementsList = elementsList2

                        notifyDataSetChanged()
//                        Log.d("Elements -> ", elementsList.toString())
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

    private fun checkOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
        return false

    }

}
