package com.example.metamask

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.util.Linkify
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.metamask.DAO.*
import com.example.metamask.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var binding: ActivityMainBinding
    val tabTitle = arrayListOf("자산", "활동")
    var addressText = ""
    var userToken = listOf(GetTokenData("BTC",1.1))
    var pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle)
    private val assetFragment = pagerAdapter.assetFragment

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val spinnerData : MutableList<String> = mutableListOf("이더리움 메인넷", "Ropsten 테스트 네트워크")

        // toolbar
        setSupportActionBar(binding.toolbar)

        // context menu
        registerForContextMenu(binding.imageMain)

        // spinner
        spinnerAdapter = SpinnerAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
        binding.spinnerNetwork.adapter = spinnerAdapter

        // tab view
        val tabLayout = binding.tabs
        val viewPager = binding.viewpager
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        // go to swap page
        binding.btnSwap.setOnClickListener {
            val intent = Intent(this, SwapActivity::class.java)
            startActivity(intent)
        }

        // support link
        val mTransform = Linkify.TransformFilter { _, _ -> "" }
        val pattern = Pattern.compile("MetaMask 지원")
        Linkify.addLinks(binding.support, pattern, null, null, mTransform)

        // address
        addressText = binding.accountAddress.text.toString()

        // copy to clipboard
        binding.accountAddress.setOnClickListener {
            createClip(addressText)
        }
    }

    // toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu)
        return true
    }

    // context menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        menu?.add(0,100,0,UserManager.userManager.firstUser.name)
        menu?.add(0,101,0,UserManager.userManager.secondUser.name)
        inflater.inflate(R.menu.account_context_menu, menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            100 -> {
                setAccount(UserManager.userManager.firstUser.name, UserManager.userManager.firstUser.address)
                userToken = UserManager.userManager.firstUser.getToken
                binding.imageMain.setImageResource(R.drawable.icon_brown)
                assetFragment.dataList.clear()
                userToken.forEach { assetFragment.dataList.add(it) }
                assetFragment.adapter.notifyDataSetChanged()
            }
            101 -> {
                setAccount(UserManager.userManager.secondUser.name, UserManager.userManager.secondUser.address)
                userToken = UserManager.userManager.secondUser.getToken
                binding.imageMain.setImageResource(R.drawable.icons_purple)
                assetFragment.dataList.clear()
                userToken.forEach { assetFragment.dataList.add(it) }
                assetFragment.adapter.notifyDataSetChanged()
            }
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    fun setAccount(name: String, address: String) {
        binding.accountName.text = name
        addressText = address
        binding.accountAddress.text = "${addressText.substring(0 until 5)}...${addressText.substring(addressText.length-4 until addressText.length)}"
    }

    fun createClip(message: String) {
        val clipManager: ClipboardManager = applicationContext
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clipData: ClipData = ClipData.newPlainText("message", message)
        clipManager.setPrimaryClip(clipData)
        Toast.makeText(applicationContext, "주소가 복사 되었습니다.", Toast.LENGTH_SHORT).show()
    }
}