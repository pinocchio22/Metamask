package com.example.metamask

import com.example.metamask.DAO.GetTokenData
import com.example.metamask.DAO.UserData

class UserManager {
    companion object {
        val userManager = UserManager()
    }
    val firstUser = UserData("account1","0x8ec209saflwhh23BEfD", listOf(
        GetTokenData("BTC", 1234.67),
        GetTokenData("ETH", 6789.12)
    ))
    val secondUser = UserData("account2","0x9ds209saflwhh44KW3L", listOf(
        GetTokenData("BTC", 123.467),
        GetTokenData("ETH", 678.912)
    ))
}