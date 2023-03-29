package com.example.metamask.Retrofit

import com.example.metamask.DAO.NetworkData
import com.example.metamask.DAO.TokenData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */


//"https://api.coingecko.com/api/v3/ coins/markets ?vs_currency=usd &order=market_cap_desc&per_page=100&page=1&sparkline=false&locale=en"

//interface TokenService {
//    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&locale=en")
//    fun getTokenData(@Query("vs_currency") symbol : String, @Query("current_price") current_price : Int) : Call<TokenData>
//}

//interface TokenService {
//    @GET("api/v3/coins/markets")
//    fun getTokenData(@Query("vs_currency") vs_currency : String) : Call<List<TokenData>>
//}

interface TokenService {
    @GET("v2/assets")
    fun getTokenData() : Call<TokenData>
}
