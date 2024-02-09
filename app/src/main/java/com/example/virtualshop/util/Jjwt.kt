package com.example.virtualshop.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts

object Jjwt {

    fun decodeJWT(jwt: String, secretKey: String): Claims?{
        try {
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.toByteArray())
                .build()
                .parseClaimsJws(jwt)
                .body

            return claims

        } catch (ex: Exception) {
            return null
        }

    }

}