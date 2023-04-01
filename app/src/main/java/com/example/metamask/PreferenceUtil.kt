package com.example.metamask

import android.content.Context
import android.content.SharedPreferences
import com.example.metamask.DAO.GetTokenData

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
class PreferenceUtil(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, Value: String):String{
        return preferences.getString(key, Value).toString()
    }

    fun setString(key: String, Value: String){
        preferences.edit().putString(key, Value).apply()
    }
}