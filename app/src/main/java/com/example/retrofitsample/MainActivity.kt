package com.example.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RestrictTo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

interface GitHubService {
    @GET("users/{user}/repos")
    fun fetchRepositories(): Call<ResponseBody>  // ResponseBodyはokhttp3のものを使用
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setupRetrofit() {
        // apply
        // オブジェクトを生成して、各種値を設定する時に使われる
        // 戻り値が対象オブジェクト
        val retrofit = Retrofit.Builder().apply {
            // URLを設定したretrofitオブジェクトを生成
            baseUrl("https://api.github.com/")
        }.build()
        val service = retrofit.create(GitHubService::class.java)
        // interfaceで用意したメソッド
        val get = service.fetchRepositories()
        
    }

}