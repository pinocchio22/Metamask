package com.example.metamask

import androidx.appcompat.app.AppCompatActivity
import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.UserData

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-12-16
 * @desc
 */
open class UserActivity : AppCompatActivity(){
    val firstUser = UserData("account1","0x8ec209saflwhh23BEfD", listOf(
        GetTokenData("BTC", 1234.67),
        GetTokenData("ETH", 6789.12)
    ))
    val secondUser = UserData("account2","0x9ds209saflwhh44KW3L", listOf(
        GetTokenData("BTC", 123.467),
        GetTokenData("ETH", 678.912)
    ))
}