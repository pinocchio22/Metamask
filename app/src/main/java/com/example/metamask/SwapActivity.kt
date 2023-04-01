package com.example.metamask

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.TokenDownloadData
import com.example.metamask.Retrofit.RetrofitConnection
import com.example.metamask.Retrofit.TokenService
import com.example.metamask.databinding.ActivitySwapBinding
import com.skydoves.balloon.*
import retrofit2.Call
import retrofit2.Callback


class SwapActivity : AppCompatActivity() {
    lateinit var binding : ActivitySwapBinding
    var tokendata : ArrayList<GetTokenData> = arrayListOf()
    var firstSearch = GetTokenData("",0.0)
    var secondSearch = GetTokenData("", 0.0)
//    lateinit var firstSearch: GetTokenData
//    lateinit var secondSearch: GetTokenData
    lateinit var preferences: PreferenceUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swap)
        preferences = PreferenceUtil(applicationContext)
//        preferences.getString("first", GetTokenData("", 0.0))
//        preferences.getString("second",GetTokenData("", 0.0))

        // get token
        getToken()

        // finish activity
        binding.cancelButton.setOnClickListener {
            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
            finish()
        }

        // search first
//        var setPreFirst = preferences.setString("first",("${firstSearch.name}+${firstSearch.price}"))
        var getPreFirst = preferences.getString("first",("${firstSearch.name}+${firstSearch.price}")).split("+")[0]
        var getPreSecond = preferences.getString("second",("${secondSearch.name}+${secondSearch.price}")).split("+")[0]
        binding.searchBtnFirst.setOnClickListener {
            var intent = Intent(this, SearchFirstActivity::class.java)
            Log.d("tokendata1", tokendata.toString())
            intent.putExtra("tokendata", tokendata)
            startActivity(intent)
            finish()
        }
        if (intent.getSerializableExtra("clickedItemFirst") != null) {
            firstSearch = intent.getSerializableExtra("clickedItemFirst") as GetTokenData
            Log.d("onClick1", firstSearch.toString())
            preferences.setString("first",("${firstSearch.name}+${firstSearch.price}"))
            Log.d("onClick111", getPreSecond)
            Log.d("onClick1111", getPreFirst)
            binding.searchBtnFirst.text = firstSearch.name
            binding.searchBtnSecond.text = getPreSecond
        }
        binding.searchUsd.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.searchUsd.text.toString() != "") {
                    binding.searchToDollor.text = (Integer.parseInt(binding.searchUsd.text.toString()) * (preferences.getString("first","").split("+")[1]).toDouble()).toString()
                } else {
                    binding.searchUsd.setText("0")
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        // search second
        binding.searchBtnSecond.setOnClickListener {
            var intent = Intent(this, SearchSecondActivity::class.java)
            Log.d("tokendata1", tokendata.toString())
            intent.putExtra("tokendata", tokendata)
            startActivity(intent)
            finish()
        }
        if (intent.getSerializableExtra("clickedItemSecond") != null) {
            secondSearch = intent.getSerializableExtra("clickedItemSecond") as GetTokenData
            Log.d("onClick2", secondSearch.toString())
            preferences.setString("second",("${secondSearch.name}+${secondSearch.price}"))
            Log.d("onClick111", getPreFirst)
            Log.d("onClick1111", getPreSecond)
            binding.searchBtnSecond.text = secondSearch.name
            binding.searchBtnFirst.text = getPreFirst
        }

        //change btn
        binding.btnChange.setOnClickListener {
            val tempFirst = preferences.getString("first","")
            val tempSecond = preferences.getString("second","")
            preferences.setString("first", tempSecond)
            preferences.setString("second", tempFirst)
            binding.searchBtnFirst.text = preferences.getString("first","").split("+")[0]
            binding.searchBtnSecond.text = preferences.getString("second","").split("+")[0]
            binding.searchToDollor.text = (Integer.parseInt(binding.searchUsd.text.toString()) * (preferences.getString("first","").split("+")[1]).toDouble()).toString()
        }


        // tooltip
        val balloon = Balloon.Builder(this)
            .setHeight(BalloonSizeSpec.WRAP)
            .setWidthRatio(0.5f)
            .setText("주문 시점과 확인 시점 사이에 가격이 변동되는 현상을 '슬리패지'라고합니다. 슬리패지가 '최대 슬리패지'설정을 초과하면 스왑이 자동으로 취소됩니다.")
            .setTextSize(10f)
            .setTextColor(Color.BLACK)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(10)
            .setBackgroundColor(Color.WHITE)
            .setCornerRadius(6f)
            .setBalloonAnimation(BalloonAnimation.FADE)
            .build()
        binding.slippageTootip.setOnClickListener {
            balloon.showAlignTop(binding.slippageTootip)
        }

        // option
        binding.option.setOnClickListener {
            binding.option.visibility = View.GONE
            binding.optionFirstLayout.visibility = View.VISIBLE
        }

        // slippage radio btn
        binding.slippageRadioGroup.setOnCheckedChangeListener { group, id ->
            when(id) {
                R.id.radio_2 -> {
                    binding.radio2.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_blue)
                    binding.radio3.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_white)
                    binding.radioCustom.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_white)
                }
                R.id.radio_3 -> {
                    binding.radio2.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_white)
                    binding.radio3.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_blue)
                    binding.radioCustom.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_white)
                }
                R.id.radio_custom -> {
                    binding.radio2.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_white)
                    binding.radio3.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_white)
                    binding.radioCustom.background = ContextCompat.getDrawable(applicationContext,R.drawable.rounded_btn_blue)
                }
            }
        }
    }

    fun getToken() {
        var retrofitAPI = RetrofitConnection.getInstance().create(TokenService::class.java)
        retrofitAPI.getTokenData().enqueue(object : Callback<TokenDownloadData> {
            override fun onResponse(
                call : Call<TokenDownloadData>, response: retrofit2.Response<TokenDownloadData>
            ) {
                Log.d("testData", response.toString())
                // 정삭적인 response 가 왔다면 UI 업데이트
                if (response.isSuccessful) {
                    Log.d("testData", "1")
                    Toast.makeText(this@SwapActivity, "최신 정보 업데이트 완료.", Toast.LENGTH_SHORT).show()
                    // response.body()가 null 이 아니면 updateAirUI()
                    response.body()?.let { it ->
                        it.data.forEach { data ->
                            tokendata.add(GetTokenData(data.symbol, data.priceUsd))
                        }
                        Log.d("tokendata11", tokendata.toString())
                    }
                    Log.d("tokendata12", tokendata.toString())
                } else {
                    Log.d("tokendata13", "2")
                    Toast.makeText(this@SwapActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                Log.d("tokendata14", tokendata.toString())
            }

            override fun onFailure(call: Call<TokenDownloadData>, t: Throwable) {
                t.printStackTrace()
                Log.d("testData", call.request().toString())
                Log.d("testData", t.message.toString())
                Toast.makeText(this@SwapActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}