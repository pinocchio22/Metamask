package com.example.metamask

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.metamask.DAO.NetworkData
import com.example.metamask.DAO.SpinnerAdapter
import com.example.metamask.DAO.TokenData
import com.example.metamask.Retrofit.RetrofitConnection
import com.example.metamask.Retrofit.TokenService
import com.example.metamask.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerAdapter: SpinnerAdapter
    private val spinnerData = ArrayList<NetworkData>()
    lateinit var token : TokenData

    var symbol = "usd"
    var currentPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        var testData : MutableList<NetworkData> = mutableListOf( NetworkData("1",Color.BLACK))

        // toolbar
        setSupportActionBar(binding.toolbar)

        // context menu
        binding.btnSwap.setOnClickListener {
            val intent = Intent(this, SwapActivity::class.java)
            startActivity(intent)
        }
        registerForContextMenu(binding.imageMain)

        // spinner
        spinnerAdapter = SpinnerAdapter(this, android.R.layout.simple_spinner_item, testData)
        binding.spinnerNetwork.adapter = spinnerAdapter

        //get token
        getToken(symbol, currentPrice)
    }

    // toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu)
        return true
    }

    // context menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.account_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.context1 -> {
                true
            }
            R.id.context2 -> {
                true
            }
            else -> false
        }
    }

    fun getToken(symbol: String, currentPrice: Int) {
        var retrofitAPI = RetrofitConnection.getInstance().create(TokenService::class.java)
        retrofitAPI.getTokenData().enqueue(object : Callback<TokenData> {
            override fun onResponse(
                call : Call<TokenData>, response: retrofit2.Response<TokenData>
            ) {
                Log.d("testData", response.toString())
                // 정삭적인 response 가 왔다면 UI 업데이트
                if (response.isSuccessful) {
                    Log.d("testData", "1")
                    Toast.makeText(this@MainActivity, "최신 정보 업데이트 완료.", Toast.LENGTH_SHORT).show()
                    // response.body()가 null 이 아니면 updateAirUI()
                    response.body()?.let { updateUI(it) }
                } else {
                    Log.d("testData", "2")
                    Toast.makeText(this@MainActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TokenData>, t: Throwable) {
                t.printStackTrace()
                Log.d("testData", call.request().toString())
                Log.d("testData", t.message.toString())
                Toast.makeText(this@MainActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateUI(tokenData: TokenData) {
        var symbol: TextView = findViewById(R.id.token_name)
        var price: TextView = findViewById(R.id.token_to_dollor)

        symbol.text = tokenData.data[0].symbol
        price.text = tokenData.data[0].priceUsd.toString()
    }
}