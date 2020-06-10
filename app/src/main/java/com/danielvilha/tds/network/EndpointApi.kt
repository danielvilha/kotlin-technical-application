package com.danielvilha.tds.network

import com.danielvilha.tds.data.Items
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by danielvilha on 10/06/20
 */
interface EndpointApi {

    @GET("employees")
    fun getEmployees(): Observable<Items>
}