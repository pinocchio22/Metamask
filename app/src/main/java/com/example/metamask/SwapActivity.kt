package com.example.metamask

import android.annotation.SuppressLint
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
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    var tokendata : ArrayList<GetTokenData> = arrayListOf()
    var firstSearch = GetTokenData("",0.0)
    var secondSearch = GetTokenData("", 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swap)
        preferences = PreferenceUtil(applicationContext)
        val getPreFirst = preferences.getString("first",("${firstSearch.name}+${firstSearch.price}")).split("+")[0]
        val getPreSecond = preferences.getString("second",("${secondSearch.name}+${secondSearch.price}")).split("+")[0]

        // get token
        getToken()

        // finish activity
        binding.cancelButton.setOnClickListener {
            Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
            finish()
        }

        // search first
        searchFirst(getPreFirst, getPreSecond)

        // search second
        searchSecond(getPreFirst, getPreSecond)

        //change btn
        setChangeBtn()

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
        setRadio()

        // complete btn
        binding.btnComplete.setOnClickListener {
            Toast.makeText(this, "검토 확인", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setChangeBtn() {
        binding.btnChange.setOnClickListener {
            val tempFirst = preferences.getString("first","")
            val tempSecond = preferences.getString("second","")
            preferences.setString("first", tempSecond)
            preferences.setString("second", tempFirst)
            binding.searchBtnFirst.text = preferences.getString("first","").split("+")[0]
            binding.searchBtnSecond.text = preferences.getString("second","").split("+")[0]
            binding.searchToDollor.text = "= $" + (Integer.parseInt(binding.searchUsd.text.toString()) * (preferences.getString("first","").split("+")[1]).toDouble()).toString()
        }
    }

    private fun setRadio() {
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
        val retrofitAPI = RetrofitConnection.getInstance().create(TokenService::class.java)
        retrofitAPI.getTokenData().enqueue(object : Callback<TokenDownloadData> {
            override fun onResponse(
                call : Call<TokenDownloadData>, response: retrofit2.Response<TokenDownloadData>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SwapActivity, "최신 정보 업데이트 완료.", Toast.LENGTH_SHORT).show()
                    response.body()?.let { it ->
                        it.data.forEach { data ->
                            tokendata.add(GetTokenData(data.symbol, data.priceUsd))
                        }
                    }
                } else {
                    Toast.makeText(this@SwapActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TokenDownloadData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@SwapActivity, "업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchFirst(getPreFirst: String, getPreSecond: String) {
        binding.searchBtnFirst.setOnClickListener {
            val intent = Intent(this, SearchFirstActivity::class.java)
            intent.putExtra("tokendata", tokendata)
            startActivity(intent)
            finish()
        }
        if (intent.getSerializableExtra("clickedItemFirst") != null) {
            firstSearch = intent.getSerializableExtra("clickedItemFirst") as GetTokenData
            preferences.setString("first",("${firstSearch.name}+${firstSearch.price}"))
            binding.searchBtnFirst.text = firstSearch.name
            binding.searchBtnSecond.text = getPreSecond
        }
        binding.searchUsd.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.searchUsd.text.toString() != "") {
                    binding.searchToDollor.text = "= $" + (Integer.parseInt(binding.searchUsd.text.toString()) * (preferences.getString("first","").split("+")[1]).toDouble()).toString()
                } else {
                    binding.searchUsd.setText("0")
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
    private fun searchSecond(getPreFirst: String, getPreSecond: String) {
        binding.searchBtnSecond.setOnClickListener {
            val intent = Intent(this, SearchSecondActivity::class.java)
            intent.putExtra("tokendata", tokendata)
            startActivity(intent)
            finish()
        }
        if (intent.getSerializableExtra("clickedItemSecond") != null) {
            secondSearch = intent.getSerializableExtra("clickedItemSecond") as GetTokenData
            preferences.setString("second",("${secondSearch.name}+${secondSearch.price}"))
            binding.searchBtnSecond.text = secondSearch.name
            binding.searchBtnFirst.text = getPreFirst
        }
    }
}