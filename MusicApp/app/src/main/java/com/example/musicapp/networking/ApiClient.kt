package com.example.musicapp.networking

import com.example.musicapp.models.Song
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {
    //CHANGE REQUEST PATH
    @GET("items") fun getElements(): Observable<List<Song>> // Observable<SongEmbedded>
    @GET("genres") fun getGenres(): Observable<List<String>>
    @GET("bought") fun getBought(): Observable<List<Song>>
    @GET("songs/{genre}") fun getSongs(@Path("genre") genre: String): Observable<List<Song>>
    @POST("item") fun addElement(@Body element: Song): Completable
    @DELETE("item/{id}") fun deleteElement(@Path("id") id: Int) : Completable
    @PUT("element/{id}") fun updateElement(@Path("id")id: Int, @Body element: Song) : Completable
    @POST("buy") fun buyElement(@Body element: Song) : Completable
    companion object {

        fun create(): ApiClient {
            //CHANGE PORT
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:2020/")
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}
