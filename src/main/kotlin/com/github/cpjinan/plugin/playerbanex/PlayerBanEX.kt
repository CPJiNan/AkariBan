package com.github.cpjinan.plugin.playerbanex

import com.github.cpjinan.plugin.playerbanex.internal.manager.RegisterManager
import taboolib.common.platform.Plugin

object PlayerBanEX : Plugin() {

    override fun onEnable() {
        RegisterManager.registerAll()
    }
}