package com.github.cpjinan.plugin.playerbanex.internal.manager

import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored
import java.time.LocalDateTime

object FormatManager {

    fun getKickFormat(name:String, reason : String, time:String, admin:String) : String{
        return ConfigManager.options.getList("kick-format")!!.joinToString(separator = "").replaceWithOrder(name, reason, time, admin).colored()
    }

    fun getBanFormat(name:String, period:String, reason : String, time:String, admin:String) : String{
        return ConfigManager.options.getList("ban-format")!!.joinToString(separator = "").replaceWithOrder(name, period, reason, time, admin).colored()
    }

}