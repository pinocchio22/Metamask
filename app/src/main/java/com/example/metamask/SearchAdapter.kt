package com.example.metamask.DAO

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metamask.R
import com.example.metamask.databinding.ItemSearchBinding



/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
class SearchAdapter(foodItemArrayList: ArrayList<GetTokenData>, activity: Activity) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder?>() {
    var tokenArrayList: ArrayList<GetTokenData>
    var activity: Activity

    init {
        this.tokenArrayList = foodItemArrayList
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val token = tokenArrayList[position]
        holder.bind(token)
    }

    override fun getItemCount(): Int {
        return tokenArrayList.size
    }

    fun filterList(filteredList: ArrayList<GetTokenData>) {
        tokenArrayList = filteredList
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GetTokenData) {
            binding.searchName.text = user.name
            binding.searchPrice.text = "$${user.price}USD"
        }
    }
}