package com.hfad.data.repository

import com.hfad.data.retrofit.ApiClient
import com.hfad.data.retrofit.ApiService

class MPRepository {
    private val retrofit = ApiClient.apiService

    fun getApi():ApiService{
        return retrofit
    }
}