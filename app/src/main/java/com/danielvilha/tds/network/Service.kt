package com.danielvilha.tds.network

import com.danielvilha.tds.data.Items
import com.danielvilha.tds.util.BASE_URL
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by danielvilha on 10/06/20
 */
class Service {

    private val endpointApi: EndpointApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        endpointApi = retrofit.create(EndpointApi::class.java)
    }

    fun getEmployees() : Observable<Items> {
        return endpointApi.getEmployees()
    }
}