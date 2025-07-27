package com.company.fetchexercise.network

import com.company.fetchexercise.models.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun fetchItems(): List<Item>
}