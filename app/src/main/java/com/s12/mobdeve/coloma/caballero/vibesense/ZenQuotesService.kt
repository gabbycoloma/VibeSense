package com.s12.mobdeve.coloma.caballero.vibesense

import retrofit2.http.GET

interface ZenQuotesService {
    @GET("api/quotes")
    suspend fun getRandomQuote(): List<Quote>
}