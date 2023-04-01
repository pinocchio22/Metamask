package com.example.metamask

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.SearchAdapterSecond
import com.example.metamask.databinding.ActivitySearchSecondBinding
import java.util.*


class SearchSecondActivity : AppCompatActivity() {
    var filteredList: ArrayList<GetTokenData> = arrayListOf()
    var linearLayoutManager: LinearLayoutManager? = null
    var searchAdapterSecond: SearchAdapterSecond? = null
    var tokenItem = arrayListOf<GetTokenData>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchSecondBinding>(this, R.layout.activity_search_second)
        tokenItem = intent.getSerializableExtra("tokendata") as ArrayList<GetTokenData>

        Log.d("tokendata2", tokenItem.toString())

        searchAdapterSecond = SearchAdapterSecond(tokenItem, this)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerviewSearch.layoutManager = linearLayoutManager
        binding.recyclerviewSearch.adapter = searchAdapterSecond

        // filter
        searchAdapterSecond!!.notifyDataSetChanged()
        binding.EditTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = binding.EditTextSearch.text.toString()
                searchFilter(searchText)
            }
            override fun afterTextChanged(editable: Editable) {
            }
        })
        var clickedIntent = Intent(this, SwapActivity::class.java)
        searchAdapterSecond?.setItemClickListener(object : SearchAdapterSecond.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                if (filteredList.isNotEmpty()) {
//                    Log.d("onClick3", filteredList[position].toString())
                    clickedIntent.putExtra("clickedItemSecond", filteredList[position])
                    startActivity(clickedIntent)
                    finish()
                } else {
//                    Log.d("onClick4", tokenItem[position].toString())
                    clickedIntent.putExtra("clickedItemSecond", tokenItem[position])
                    startActivity(clickedIntent)
                    finish()
                }
            }
        })
    }


    fun searchFilter(searchText: String) {
        filteredList.clear()
        for (i in 0 until tokenItem.size) {
            if (tokenItem[i].name.toLowerCase().contains(searchText.lowercase(Locale.getDefault()))
            ) {
                Log.d("필터", tokenItem[i].toString())
                filteredList.add(tokenItem[i])
            }
        }
        Log.d("필터2", filteredList.toString())
        searchAdapterSecond?.filterList(filteredList)
    }
}