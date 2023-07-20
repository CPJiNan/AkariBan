package com.github.cpjinan.manager

import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored
import java.time.LocalDateTime

object FormatManager {

    fun getKickFormat(name:String, reason : String, time:String) : String{
        return ConfigManager.kick.getList("format")!!.joinToString(separator = "").replaceWithOrder(reason, name, time).colored()
    }

    fun getBanFormat(name : String, reason : String, time:String) : String{
        return ConfigManager.ban.getList("format")!!.joinToString(separator = "").replaceWithOrder(reason, name, time).colored()
    }

    fun getWhitelistFormat(name : String) : String{
        return ConfigManager.whitelist.getList("format")!!.joinToString(separator = "").replaceWithOrder(name, LocalDateTime.now().toString()).colored()
    }

}