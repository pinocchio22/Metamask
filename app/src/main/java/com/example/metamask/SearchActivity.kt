package com.example.metamask

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.SearchAdapter
import com.example.metamask.databinding.ActivitySearchBinding
import java.util.*


class SearchActivity : AppCompatActivity() {
    var filteredList: ArrayList<GetTokenData> = arrayListOf()
    var linearLayoutManager: LinearLayoutManager? = null
    var searchAdapter: SearchAdapter? = null
    var tokenItem = arrayListOf<GetTokenData>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
        tokenItem = intent.getSerializableExtra("tokendata") as ArrayList<GetTokenData>

        Log.d("tokendata2", tokenItem.toString())

        searchAdapter = SearchAdapter(tokenItem!!, this)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerviewSearch.layoutManager = linearLayoutManager
        binding.recyclerviewSearch.adapter = searchAdapter

        searchAdapter!!.notifyDataSetChanged()
        binding.EditTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val searchText = binding.EditTextSearch.text.toString()
                searchFilter(searchText)
            }
        })
    }

    fun searchFilter(searchText: String) {
        filteredList.clear()
        for (i in 0 until tokenItem.size) {
            if (tokenItem[i].name.toLowerCase().contains(
                    searchText.lowercase(
                        Locale.getDefault()
                    )
                )
            ) {
                filteredList.add(tokenItem[i])
            }
        }
        searchAdapter?.filterList(filteredList)
    }
}