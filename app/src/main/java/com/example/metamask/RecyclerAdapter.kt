package com.example.metamask

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.databinding.ItemRecyclerviewAssetBinding

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
class RecyclerAdapter2: RecyclerView.Adapter<Holder2>() {
    var tokenList = mutableListOf<GetTokenData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : Holder2 {
        val binding = ItemRecyclerviewAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder2(binding)
    }

    override fun onBindViewHolder(holder: Holder2, position: Int) {
        val token = tokenList[position]
        Log.d("recyclerview3", token.toString())
        Log.d("recyclerview6", tokenList.toString())
        Log.d("recyclerview7", tokenList.size.toString())
        holder.bind(token)
    }

    override fun getItemCount(): Int {
        return tokenList.size
    }
}

class Holder2(val binding: ItemRecyclerviewAssetBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(user: GetTokenData) {
        Log.d("recyclerview4", user.name)
        binding.itemName.text = user.name
        binding.itemDollor.text = "$${user.price}USD"
    }
}