package com.github.cpjinan.plugin.akariban.utils

import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akarilib.utils.DebugUtil
import taboolib.platform.BukkitPlugin

object DebugUtil {
    fun printArgs(vararg args: Pair<String, Any?>) {
        if (ConfigManager.isDebugEnabled()) DebugUtil.printArgs(BukkitPlugin.getInstance(), *args)
    }

    fun printInfo(vararg info: String) {
        if (ConfigManager.isDebugEnabled()) DebugUtil.printInfo(BukkitPlugin.getInstance(), *info)
    }
}