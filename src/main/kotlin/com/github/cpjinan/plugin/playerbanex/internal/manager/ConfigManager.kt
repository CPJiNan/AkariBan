package com.github.cpjinan.plugin.playerbanex.internal.manager

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

object ConfigManager {
    private const val configFile = "config.yml"
    private const val databaseFile = "database.yml"
    private const val logFile = "logs.yml"

    @Config(configFile, autoReload = true)
    lateinit var config : Configuration
    @ConfigNode("options", configFile)
    lateinit var options: ConfigurationSection

    @Config(databaseFile, autoReload = true)
    lateinit var data : Configuration
    @ConfigNode("ban-list", databaseFile)
    lateinit var banlist: ConfigurationSection

    @Config(logFile, autoReload = true)
    lateinit var log : Configuration


}