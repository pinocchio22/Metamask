package com.example.metamask.DAO

import com.google.gson.annotations.SerializedName

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
data class TokenData (@SerializedName(value = "name")var name: String, @SerializedName(value = "price")var price: Double) : java.io.Serializable
