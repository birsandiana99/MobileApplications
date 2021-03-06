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
import kotlinx.android.synthetic.main.report_view.view.*
import okhttp3.ResponseBody
import retrofit2.HttpException

class EntityReportAdapter(val context: Context) :
    RecyclerView.Adapter<EntityReportAdapter.ElementReportAdapter>()
    {

        val client by lazy { ApiClient.create() }
        var elementsList: ArrayList<Song> = ArrayList()

        init {
            refreshElements()
        }


        class ElementReportAdapter(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): EntityReportAdapter.ElementReportAdapter {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.report_view, parent, false)

            return ElementReportAdapter(view)
        }

        override fun onBindViewHolder(holder: ElementReportAdapter, position: Int) {
            // prepare details and special operation activity
//            Log.d("AAAAAAAAAAAAA",elementsList[position].status.toString())
            holder.view.name.text = "Name " + elementsList[position].name
            holder.view.status.text = "Status " + elementsList[position].status
            holder.view.quantity.text = "Quantity " + elementsList[position].quantity.toString()
            holder.view.price.text = "Price " + elementsList[position].price.toString()


            holder.view.setOnClickListener {
                showBuyDialog(holder, elementsList[position])
            }


        }

        private fun buyElement(element: Song) { // delete
            if (checkOnline()) {
                client.buyElement(element!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            refreshElements()
                        Log.d("Element bought -> ", element.toString())
                        },
                        { throwable ->
                            Toast.makeText(context, "Delete error: ${throwable.message}", Toast.LENGTH_LONG).show()
                        }
                    )
            } else {
                Toast.makeText(context, "Not online!", Toast.LENGTH_LONG).show()
            }

        }

        private fun showBuyDialog(holder: ElementReportAdapter, element: Song) { // confirm delete
            val dialogBuilder = AlertDialog.Builder(holder.view.context)
            dialogBuilder.setTitle("Buy")
            dialogBuilder.setMessage("Confirm buy?")
            dialogBuilder.setPositiveButton("Buy") { _, _ ->
                buyElement(element)
            }
            dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            val b = dialogBuilder.create()
            b.show()
        }

        override fun getItemCount() = elementsList.size

        private fun refreshElements() {
            if (checkOnline()) {
                client.getElements() // /songs/:genre
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            elementsList.clear()
                            for ( el in result)
                            {
                                if(el.status == "desired" || el.status=="needed")
                                    elementsList.add(el)
                            }
                            elementsList.sortBy { T -> T.price; T.quantity}
//                            var elementsList2: ArrayList<Song> = ArrayList()
//                            elementsList2.add(elementsList[0])
//                            elementsList2.add(elementsList[1])
//                            elementsList2.add(elementsList[2])
//                            elementsList = elementsList2
//                            elementsList.addAll(result)
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
