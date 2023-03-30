package com.example.metamask.DAO

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.annotation.LayoutRes
import com.example.metamask.databinding.ItemSpinnerNetworkBinding

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
class SpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val values: MutableList<NetworkData>
) : ArrayAdapter<NetworkData>(context, resId, values) {

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerNetworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {
            binding.spinnerColor.setBackgroundColor(model.color)
            binding.spinnerName.text = model.name
        }catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

}