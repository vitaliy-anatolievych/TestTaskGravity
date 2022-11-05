package com.testtask.testtaskgravity.data.remote.service

import com.testtask.testtaskgravity.domain.models.DefaultLinks
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    companion object {

        // methods
        private const val PROD = "prod"
    }

    @GET(PROD)
    fun getDefaultLinks(): Call<DefaultLinks>
}