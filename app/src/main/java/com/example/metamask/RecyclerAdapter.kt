package com.example.metamask


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.metamask.DAO.UserData
import com.example.metamask.databinding.ItemRecyclerviewAssetBinding


/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
class RecyclerAdapter: RecyclerView.Adapter<Holder>() {
    var tokenList = mutableListOf<UserData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : Holder {
        val binding = ItemRecyclerviewAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
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

class Holder(val binding: ItemRecyclerviewAssetBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserData) {
        Log.d("recyclerview4", user.getToken[position].name)
        binding.itemName.text = user.getToken[position].name
        binding.itemDollor.text = user.getToken[position].price.toString()
    }
}
