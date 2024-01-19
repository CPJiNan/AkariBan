package com.github.cpjinan.plugin.akariban

import com.github.cpjinan.plugin.akariban.internal.manager.DatabaseManager
import com.github.cpjinan.plugin.akariban.internal.manager.RegisterManager
import taboolib.common.platform.Plugin

object AkariBan : Plugin() {
    override fun onEnable() {
        RegisterManager.registerAll()
    }

    override fun onDisable() {
        DatabaseManager.getDatabase().save()
    }
}