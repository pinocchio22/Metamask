package com.example.metamask.DAO

import android.media.Image

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
//data class TokenData(var symbol: String, var current_price: Int,)
data class TokenData(var data : List<Data>) {
    data class Data( var symbol: String, var priceUsd: Double,)
}
