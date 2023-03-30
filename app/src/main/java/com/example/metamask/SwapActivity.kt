package com.example.metamask

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.TokenData
import com.example.metamask.DAO.TokenDownloadData
import com.example.metamask.Retrofit.RetrofitConnection
import com.example.metamask.Retrofit.TokenService
import com.example.metamask.databinding.ActivitySwapBinding
import com.skydoves.balloon.*
import retrofit2.Call
import retrofit2.Callback


class SwapActivity : AppCompatActivity() {

    var tokendata : ArrayList<GetTokenData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySwapBinding>(this, R.layout.activity_swap)

        // get token
        getToken()

        // finish activity
        binding.cancelButton.setOnClickListener {
            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
            finish()
        }

        // search
        binding.searchBtn.setOnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            Log.d("tokendata1", tokendata.toString())
            intent.putExtra("tokendata", tokendata)
            startActivity(intent)
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
                    response.body()?.let {
                        it.data.forEach {
                            tokendata.add(GetTokenData(it.symbol, it.priceUsd))
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