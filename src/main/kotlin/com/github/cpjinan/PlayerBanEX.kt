package com.github.cpjinan

import com.github.cpjinan.manager.DebugManager
import com.github.cpjinan.manager.RegisterManager
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.info
import taboolib.platform.BukkitPlugin

object PlayerBanEX : Plugin() {

    override fun onEnable() {
        DebugManager.logoPrint()
        RegisterManager.registerAll()
    }

    override fun onDisable() {
    }

}