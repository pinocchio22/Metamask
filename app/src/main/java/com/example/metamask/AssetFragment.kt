package com.example.metamask

import android.annotation.SuppressLint
import android.content.Context
import android.media.session.MediaSession.Token
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.TokenData
import com.example.metamask.DAO.UserData
import com.example.metamask.databinding.FragmentAssetBinding


class AssetFragment : Fragment() {
    private lateinit var binding: FragmentAssetBinding
    lateinit var mainActivity : MainActivity

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asset, container, false)

        var tokenData = getData2()
        var adapter = RecyclerAdapter2()
//        adapter.tokenList = tokenData
        adapter.tokenList = tokenData
        binding.recyclerviewAsset.adapter = adapter
        binding.recyclerviewAsset.layoutManager = LinearLayoutManager(context)

        // refresh
        binding.refresh.setOnClickListener {
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

//    fun getData(): MutableList<UserData> {
//        val dataList = mutableListOf<UserData>()
//        val userList = UserData("12345", listOf(TokenData("BTC", 1234.67), TokenData("ETH", 6789.12)))
//        for (i in 0 until userList.getToken.size) {
//            val name = userList.getToken[i].name
//            val price = userList.getToken[i].price
//            val token = UserData(userList.address, listOf(TokenData(name, price)))
//            dataList.add(token)
//        }
//        Log.d("recyclerview2", dataList.toString())
//        return dataList
//    }

    fun getData2(): MutableList<GetTokenData> {
        val dataList = mutableListOf<GetTokenData>()
        val firstTokenList = listOf(GetTokenData("BTC", 1234.67),GetTokenData("ETH", 6789.12))
        val secondTokenList = listOf(GetTokenData("BTC", 123.467),GetTokenData("ETH", 678.912))
        for (i in 0 until firstTokenList.size) {
            val name = firstTokenList[i].name
            val price = firstTokenList[i].price
            val token = GetTokenData(name, price)
            dataList.add(token)
        }
        Log.d("recyclerview2", dataList.toString())
        return dataList
    }
}