package com.example.virtualshop.localstorage

import android.content.Context

class Prefs(val context: Context) {
    val STORAGE_NAME = "db"
    val JWTOKEN = "jwt"
    val SECRET_KEY = "secret"

    val storage = context.getSharedPreferences(STORAGE_NAME, 0)

    public fun setJWT(jwtValue: String){
        storage.edit().putString(JWTOKEN, jwtValue).apply()
    }

    public fun getJWT(): String{
        return storage.getString(JWTOKEN, "")!!
    }

    public fun clearStorage(){
        storage.edit().clear().apply()
    }


}