package com.example.metamask

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.metamask.DAO.NetworkData
import com.example.metamask.DAO.SpinnerAdapter
import com.example.metamask.DAO.TokenData
import com.example.metamask.DAO.TokenDownloadData
import com.example.metamask.Retrofit.RetrofitConnection
import com.example.metamask.Retrofit.TokenService
import com.example.metamask.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerAdapter: SpinnerAdapter
    val tabTitle = arrayListOf("자산", "활동")

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

        // tab view
        val tabLayout = binding.tabs
        val viewPager = binding.viewpager

        viewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        // go to swap page
        binding.btnSwap.setOnClickListener {
            var intent = Intent(this, SwapActivity::class.java)
            startActivity(intent)
        }

        // support link
        val mTransform = Linkify.TransformFilter { _, _ -> "" }
        val pattern = Pattern.compile("MetaMask 지원")
        Linkify.addLinks(binding.support, pattern, null, null, mTransform)

        // address
        val addressText =  binding.accountAddress.text
        binding.accountAddress.text = "${addressText.substring(0 until 5)}...${addressText.substring(addressText.length-4 until addressText.length)}"


        // copy to clipboard
        binding.accountAddress.setOnClickListener {
            createClip(addressText.toString())
        }
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

//    fun getToken() {
//        var retrofitAPI = RetrofitConnection.getInstance().create(TokenService::class.java)
//        retrofitAPI.getTokenData().enqueue(object : Callback<TokenDownloadData> {
//            override fun onResponse(
//                call : Call<TokenDownloadData>, response: retrofit2.Response<TokenDownloadData>
//            ) {
//                Log.d("testData", response.toString())
//                // 정삭적인 response 가 왔다면 UI 업데이트
//                if (response.isSuccessful) {
//                    Log.d("testData", "1")
//                    Toast.makeText(this@MainActivity, "최신 정보 업데이트 완료.", Toast.LENGTH_SHORT).show()
//                    // response.body()가 null 이 아니면 updateAirUI()
//                    response.body()?.let {
//                        it.data.forEach {
//                            tokendata.add(TokenData(it.symbol, it.priceUsd))
//                        }
//                        Log.d("토큰데이터2", tokendata.toString())
//                    }
//                    Log.d("토큰데이터3", tokendata.toString())
//                } else {
//                    Log.d("testData", "2")
//                    Toast.makeText(this@MainActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                }
//                Log.d("토큰데이터4", tokendata.toString())
//            }
//
//            override fun onFailure(call: Call<TokenDownloadData>, t: Throwable) {
//                t.printStackTrace()
//                Log.d("testData", call.request().toString())
//                Log.d("testData", t.message.toString())
//                Toast.makeText(this@MainActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    fun updateUI(tokenDownloadData: TokenDownloadData) {
        var symbol: TextView = findViewById(R.id.token_name)
        var price: TextView = findViewById(R.id.token_to_dollor)

        symbol.text = tokenDownloadData.data[0].symbol
        price.text = tokenDownloadData.data[0].priceUsd.toString()
    }

    fun createClip(message: String) {
        val clipManager: ClipboardManager = applicationContext
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipdata: ClipData = ClipData.newPlainText("message", message)
        clipManager.setPrimaryClip(clipdata)
        Toast.makeText(applicationContext, "주소가 복사 되었습니다.", Toast.LENGTH_SHORT).show()
    }
}