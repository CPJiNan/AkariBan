package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToLocalDateTime
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToString
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.parseTime
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.module.database.getHost

object ConfigManager {
    @Config("settings.yml", autoReload = false)
    lateinit var settings: ConfigFile

    // Config Version
    const val VERSION = 2

    // Config initialization
    fun initializeConfig() {
        newFile(getDataFolder(), "lang/settings/zh_CN.yml", create = true)
        newFile(getDataFolder(), "lang/settings/en_US.yml", create = true)
    }

    // Options
    fun getConfigVersion() = settings.getInt("Options.Config-Version")
    fun isEnabledCheckUpdate() = settings.getBoolean("Options.Check-Update")
    fun isEnabledSendMetrics() = settings.getBoolean("Options.Send-Metrics")
    fun isEnabledDebug() = settings.getBoolean("Options.Debug")
    fun isEnabledWhitelist() = settings.getBoolean("Options.Whitelist")
    fun getPlayerID() = settings.getString("Options.Player-ID")
    fun getDefaultKickReason() = settings.getString("Options.Default-Value.Kick-Reason")!!
    fun getDefaultBanReason() = settings.getString("Options.Default-Value.Ban-Reason")!!
    fun getTimeFormat() = settings.getConfigurationSection("Options.Time-Format")!!
    fun getUnbanTime(banTime: String, banDuration: String): String {
        return if (banTime.isBlank()) ""
        else banTime.formatToLocalDateTime(FormatManager.getTimeFormat())!!
            .plusSeconds(
                banDuration.parseTime(
                    second = getTimeFormat().getString("Duration.Second")!!,
                    minute = getTimeFormat().getString("Duration.Minute")!!,
                    hour = getTimeFormat().getString("Duration.Hour")!!,
                    day = getTimeFormat().getString("Duration.Day")!!,
                    week = getTimeFormat().getString("Duration.Week")!!,
                    month = getTimeFormat().getString("Duration.Month")!!,
                    year = getTimeFormat().getString("Duration.Year")!!
                )
            ).formatToString(FormatManager.getTimeFormat())
    }

    // Database
    fun getMethod() = settings.getString("Database.Method")
    fun getJsonSection() = settings.getConfigurationSection("Database.JSON")!!
    fun getCborSection() = settings.getConfigurationSection("Database.CBOR")!!
    fun getSqlHost() = settings.getHost("Database.SQL")
    fun getSqlTable() = settings.getString("Database.SQL.table")!!

    // Message
    fun getKickFormat() = settings.getStringList("Message-Format.Kick")
    fun getBanFormat() = settings.getStringList("Message-Format.Ban")
    fun getWhitelistFormat() = settings.getStringList("Message-Format.Whitelist")


}