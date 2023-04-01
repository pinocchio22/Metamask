package com.example.metamask

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.databinding.FragmentAssetBinding
import java.util.ArrayList


class AssetFragment : Fragment() {
    private lateinit var binding: FragmentAssetBinding
    lateinit var mainActivity : MainActivity
    var adapter = RecyclerAdapter2()
    val firstTokenList = listOf(GetTokenData("BTC", 1234.67),GetTokenData("ETH", 6789.12))
    var userToken = arguments?.getSerializable("userToken") as GetTokenData?

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asset, container, false)

        var tokenData = getData2(firstTokenList)
        adapter.tokenList = tokenData
        binding.recyclerviewAsset.adapter = adapter
        binding.recyclerviewAsset.layoutManager = LinearLayoutManager(context)

        // refresh
        binding.refresh.setOnClickListener {
            adapter.notifyDataSetChanged()
        }
//        (activity as MainActivity?)?.refreshViewpager()

        Log.d("어댑터555", userToken.toString())
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

    fun getData2(list: List<GetTokenData>): MutableList<GetTokenData> {
        val dataList = mutableListOf<GetTokenData>()
//        val secondTokenList = listOf(GetTokenData("BTC", 123.467),GetTokenData("ETH", 678.912))
        for (i in list.indices) {
            val name = list[i].name
            val price = list[i].price
            val token = GetTokenData(name, price)
            dataList.add(token)
        }
        Log.d("recyclerview2", dataList.toString())
        return dataList
    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        Log.d("어댑터4444", userToken.toString())
//        getData2(userToken!!)
        ft.detach(fragment).attach(fragment).commit()

    }
}