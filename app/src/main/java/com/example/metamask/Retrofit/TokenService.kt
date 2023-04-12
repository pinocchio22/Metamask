package com.example.metamask.Retrofit

import com.example.metamask.DAO.TokenDownloadData
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
interface TokenService {
    @GET("v2/assets")
    fun getTokenData() : Call<TokenDownloadData>
}
