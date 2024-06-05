package com.github.cpjinan.plugin.akariban.internal

import com.github.cpjinan.plugin.akariban.AkariBan.plugin
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akariban.internal.manager.DatabaseManager
import com.github.cpjinan.plugin.akariban.utils.LoggerUtil
import com.github.cpjinan.plugin.akariban.utils.UpdateUtil
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.common.platform.function.console
import taboolib.module.chat.colored
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics

object PluginLoader {
    @Awake(LifeCycle.LOAD)
    fun load() {
        console().sendLang("Plugin-Loading", plugin.description.version)
        Metrics(18992, plugin.description.version, Platform.BUKKIT)
    }

    @Awake(LifeCycle.ENABLE)
    fun enable() {
        LoggerUtil.message(
            "",
            "&o      _    _              _ ____                ".colored(),
            "&o     / \\  | | ____ _ _ __(_) __ )  __ _ _ __    ".colored(),
            "&o    / _ \\ | |/ / _` | '__| |  _ \\ / _` | '_ \\   ".colored(),
            "&o   / ___ \\|   < (_| | |  | | |_) | (_| | | | |  ".colored(),
            "&o  /_/   \\_\\_|\\_\\__,_|_|  |_|____/ \\__,_|_| |_|  ".colored(),
            ""
        )
        ConfigManager.initializeConfig()
        DatabaseManager.getDatabase().save()
        console().sendLang("Plugin-Enabled")
        UpdateUtil.getPluginNotice()
        UpdateUtil.getPluginUpdate()
        UpdateUtil.getConfigUpdate()
    }

    @Awake(LifeCycle.DISABLE)
    fun disable() {
        DatabaseManager.getDatabase().save()
        console().sendLang("Plugin-Disable")
    }

}