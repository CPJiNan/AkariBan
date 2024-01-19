package com.github.cpjinan.plugin.akariban.internal.manager

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

object ConfigManager {
    @Config("config.yml")
    lateinit var config: ConfigFile

    fun isUpdateEnabled(): Boolean = config.getBoolean("options.update")
    fun isMetricsEnabled(): Boolean = config.getBoolean("options.metrics")
    fun isDebugEnabled(): Boolean = config.getBoolean("options.debug")
    fun getDefaultKickReason(): String = config.getString("options.default-value.kick-reason")!!
    fun getDefaultBanReason(): String = config.getString("options.default-value.ban-reason")!!
}