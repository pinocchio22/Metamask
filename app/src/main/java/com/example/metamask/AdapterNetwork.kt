package com.example.metamask.DAO

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.example.metamask.R
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
    private val values: MutableList<String>
) : ArrayAdapter<String>(context, resId, values) {

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerNetworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {
            if (position == 1) binding.spinnerColor.setImageResource(R.drawable.circle_blue) else binding.spinnerColor.setImageResource(R.drawable.circle_green)
            binding.spinnerName.text = model
        }catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

}