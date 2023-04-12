package com.example.metamask.DAO

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
data class TokenDownloadData(var data : List<Data>) {
    data class Data( var symbol: String, var priceUsd: Double,)
}
