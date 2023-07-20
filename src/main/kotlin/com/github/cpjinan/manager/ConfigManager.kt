package com.github.cpjinan.manager

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

object ConfigManager {
    private const val configFile = "config.yml"
    private const val databaseFile = "database/database.yml"

    @Config(configFile, autoReload = true)
    lateinit var config : Configuration
    @ConfigNode("options", configFile)
    lateinit var options: ConfigurationSection

    @Config(databaseFile, autoReload = true)
    lateinit var database : Configuration
    @ConfigNode("database", databaseFile)
    lateinit var data: ConfigurationSection

    @ConfigNode("module.kick", configFile)
    lateinit var kick: ConfigurationSection
    @ConfigNode("module.ban", configFile)
    lateinit var ban: ConfigurationSection
    @ConfigNode("module.whitelist", configFile)
    lateinit var whitelist: ConfigurationSection

}