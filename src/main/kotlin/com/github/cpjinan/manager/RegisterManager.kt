package com.github.cpjinan.manager

import com.github.cpjinan.PlayerBanEX
import taboolib.common.io.newFile
import taboolib.common.platform.Platform
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.info
import taboolib.expansion.setupPlayerDatabase
import taboolib.module.metrics.Metrics
import taboolib.platform.BukkitPlugin

object RegisterManager {

    /**
     * 快捷注册方法
     */
    fun registerAll() {
        registerMetrics()
        registerDatabase()
    }

    /**
     * bStats统计注册方法
     */
    private fun registerMetrics() {
        if (ConfigManager.options.getBoolean("metrics")) Metrics(18992, BukkitPlugin.getInstance().description.version, Platform.BUKKIT)
    }

    /**
     * 数据库注册方法
     */
    private fun registerDatabase() {
        if (ConfigManager.options.getString("database.use").equals("LOCAL")) { setupPlayerDatabase(newFile(getDataFolder(), "database/database.db")); return }
        else if (ConfigManager.options.getString("database.use").equals("SQL")) ConfigManager.options.getConfigurationSection("database.sql")?.let { setupPlayerDatabase(it) }
    }

    /**
     * 插件正版验证注册方法
     */
    private fun registerVerify() {
        if(ConfigManager.options.getString("license")?.length!=36) {
            info("无效的许可证！已自动禁用PlayerBanEX...")
            BukkitPlugin.getInstance().server.pluginManager.getPlugin("PlayerBanEX")?.let { BukkitPlugin.getInstance().server.pluginManager.disablePlugin(it) }
        }

    }

}