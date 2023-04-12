package com.s12.mobdeve.coloma.caballero.vibesense.Utility

import com.s12.mobdeve.coloma.caballero.vibesense.Model.Quote
import retrofit2.http.GET

interface ZenQuotesService {
    @GET("api/quotes")
    suspend fun getRandomQuote(): List<Quote>
}