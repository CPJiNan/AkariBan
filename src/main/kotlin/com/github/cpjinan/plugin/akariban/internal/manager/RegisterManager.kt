package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akariban.utils.MetricsUtil
import taboolib.common.platform.function.info

object RegisterManager {
    fun registerAll() {
        registerLogo()
        UpdateManager.checkUpdate()
        DatabaseManager.getDatabase().save()
        if (ConfigManager.isMetricsEnabled()) MetricsUtil.registerBukkitMetrics(18992)
    }

    private fun registerLogo() {
        info("      _    _              _ ____                ")
        info("     / \\  | | ____ _ _ __(_) __ )  __ _ _ __    ")
        info("    / _ \\ | |/ / _` | '__| |  _ \\ / _` | '_ \\   ")
        info("   / ___ \\|   < (_| | |  | | |_) | (_| | | | |  ")
        info("  /_/   \\_\\_|\\_\\__,_|_|  |_|____/ \\__,_|_| |_|  ")
    }
}