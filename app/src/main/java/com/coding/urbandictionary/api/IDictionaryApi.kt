package com.coding.urbandictionary.api

import com.coding.urbandictionary.model.DictionaryList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IDictionaryApi {
    @Headers("x-rapidapi-key:" +  "a07af40954msh992c90d11f0b7a0p178c9bjsn152f8a3f247a", "x-rapidapi-host:" +  "mashape-community-urban-dictionary.p.rapidapi.com" )
    @GET("/define")
    suspend fun getSearchData(
        @Query("term") trm: String): Response<DictionaryList>
}