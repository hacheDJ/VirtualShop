package com.example.virtualshop.networking

import com.example.virtualshop.dto.DetailOfUserRes
import com.example.virtualshop.dto.PayReq
import com.example.virtualshop.dto.PayRes
import com.example.virtualshop.dto.SigninReq
import com.example.virtualshop.dto.SigninRes
import com.example.virtualshop.dto.SignupReq
import com.example.virtualshop.dto.SignupRes
import com.example.virtualshop.localstorage.GlobalAppication
import com.example.virtualshop.model.Category
import com.example.virtualshop.model.Order
import com.example.virtualshop.model.Product
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

object Api {
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://funny-trench-coat-hen.cyclic.app/api/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())

    interface RemoteService{
        @GET("product/categories/{des}")
        suspend fun getProductsByCategory(@Path("des") description: String): Response<List<Product>>

        @GET("category")
        suspend fun getCategories(): Response<List<Category>>

        @GET("product/find/{name}")
        suspend fun findProductsByName(@Path("name") nameProduct: String): Response<List<Product>>

        @POST("card/pay")
        suspend fun pay(@Body payReq: PayReq): Response<PayRes>

        //@Headers("Authorization: ")
        @POST("user/signin")
        suspend fun signin(@Body signinReq: SigninReq): Response<SigninRes>

        //@Headers("Authorization: ")
        @POST("user/signup")
        suspend fun signup(@Body signupReq: SignupReq): Response<SignupRes>

        @GET("user/detail")
        suspend fun detailOfUser(): Response<DetailOfUserRes>

        @GET("order/user")
        suspend fun listOrdersByUser(): Response<List<Order>>

        //@GET("api/Producto")
        //suspend fun getProductById(@Query("codigo") code:Int): Response<Product>

        //@GET("api/Producto/{codigo}")
        //suspend fun getProductById(@Path("codigo") code:Int): Response<Product>

        //@POST("api/Producto")
        //suspend fun saveProduct(@Body request: RegisterProductRequest) : Response<Result>
    }

    fun build():RemoteService{
        return builder.build().create(RemoteService::class.java)
    }

    private fun getClient(): OkHttpClient{
        return OkHttpClient().newBuilder().addInterceptor(HeaderInterceptor()).build()
    }

}