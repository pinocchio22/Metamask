package com.example.metamask.DAO

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.metamask.databinding.ItemSearchBinding



/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
class SearchAdapterFirst(foodItemArrayList: ArrayList<GetTokenData>, activity: Activity) :
    RecyclerView.Adapter<SearchAdapterFirst.ViewHolder?>() {
    private lateinit var binding: ItemSearchBinding
    private lateinit var itemClickListner: ItemClickListener
    var tokenArrayList: ArrayList<GetTokenData>
    var activity: Activity

    init {
        this.tokenArrayList = foodItemArrayList
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val token = tokenArrayList[position]
        holder.bind(token)
        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return tokenArrayList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<GetTokenData>) {
        tokenArrayList = filteredList
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    inner class ViewHolder(var binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: GetTokenData) {
            binding.searchName.text = user.name
            binding.searchPrice.text = "$${user.price}USD"
        }
    }
}