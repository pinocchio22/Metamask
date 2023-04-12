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
import com.example.metamask.DAO.SearchAdapterFirst
import com.example.metamask.databinding.ActivitySearchFirstBinding
import java.util.*


class SearchFirstActivity : AppCompatActivity() {
    var filteredList: ArrayList<GetTokenData> = arrayListOf()
    var linearLayoutManager: LinearLayoutManager? = null
    var searchAdapterFirst: SearchAdapterFirst? = null
    var tokenItem = arrayListOf<GetTokenData>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchFirstBinding>(this, R.layout.activity_search_first)
        tokenItem = intent.getSerializableExtra("tokendata") as ArrayList<GetTokenData>

        searchAdapterFirst = SearchAdapterFirst(tokenItem, this)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerviewSearch.layoutManager = linearLayoutManager
        binding.recyclerviewSearch.adapter = searchAdapterFirst

        // filter
        searchAdapterFirst!!.notifyDataSetChanged()
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
        val clickedIntent = Intent(this, SwapActivity::class.java)
        searchAdapterFirst?.setItemClickListener(object : SearchAdapterFirst.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                if (filteredList.isNotEmpty()) {
                    clickedIntent.putExtra("clickedItemFirst", filteredList[position])
                    startActivity(clickedIntent)
                    finish()
                } else {
                    clickedIntent.putExtra("clickedItemFirst", tokenItem[position])
                    startActivity(clickedIntent)
                    finish()
                }
            }
        })
    }


    fun searchFilter(searchText: String) {
        filteredList.clear()
        for (i in 0 until tokenItem.size) {
            if (tokenItem[i].name.lowercase(Locale.getDefault()).contains(searchText.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(tokenItem[i])
            }
        }
        searchAdapterFirst?.filterList(filteredList)
    }
}