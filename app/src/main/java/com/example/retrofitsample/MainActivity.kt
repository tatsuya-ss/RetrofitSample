package com.example.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RestrictTo
import com.example.retrofitsample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 通信するクラス
data class GitHub(val login: String,
                  val name: String,
                  val twitter_username: String)

interface GitHubService {
    @GET("users/tatsuya-ss")
    fun fetchRepositories(): Call<GitHub>  // GitHub型に変換して取得
//    fun fetchRepositories(): Call<ResponseBody>  // ResponseBodyはokhttp3のものを使用
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupButton()

    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupButton() {
        binding.button.setOnClickListener {
            fetch()
        }
    }

    private fun fetch() {
        // apply
        // オブジェクトを生成して、各種値を設定する時に使われる
        // 戻り値が対象オブジェクト
        val retrofit = Retrofit.Builder().apply {
            // URLを設定したretrofitオブジェクトを生成
            baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
        }.build()
        val service = retrofit.create(GitHubService::class.java)
        // interfaceで用意したメソッド
        val get = service.fetchRepositories()
        GlobalScope.launch {
            println("開始")
            withContext(Dispatchers.IO) {
                val responseBody = get.execute()
                responseBody.body()?.let {
                    println(it)
                }
            }
            println("終了")
        }

    }

}