package com.example.metamask

import android.annotation.SuppressLint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.databinding.FragmentAssetBinding


class AssetFragment : Fragment() {
    private lateinit var binding: FragmentAssetBinding
    private lateinit var mainActivity : MainActivity
    var adapter = RecyclerAdapter()
    val dataList = mutableListOf<GetTokenData>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asset, container, false)

        val tokenData = getData(UserManager.userManager.firstUser.getToken)
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

    private fun getData(list: List<GetTokenData>): MutableList<GetTokenData> {
        for (i in list.indices) {
            val name = list[i].name
            val price = list[i].price
            val token = GetTokenData(name, price)
            dataList.add(token)
        }
        return dataList
    }
}