package com.github.cpjinan.plugin.akariban.internal.manager

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored

object FormatManager {
    fun getKickFormat(
        player: Player,
        kickReason: String = "",
        kickTime: String = "",
        kickingAdmin: String = ""
    ): String {
        val kickMessage =
            ConfigManager.config.getStringList("options.message-format.kick").joinToString(separator = "")
        return kickMessage.replaceWithOrder(player.name, player.uniqueId, kickReason, kickingAdmin, kickTime).colored()
    }

    fun getBanFormat(
        player: Player,
        banReason: String = "",
        banDuration: String = "",
        banTime: String = "",
        unbanTime: String = "",
        banningAdmin: String = ""
    ): String {
        val banMessage =
            ConfigManager.config.getStringList("options.message-format.ban").joinToString(separator = "")
        return banMessage.replaceWithOrder(
            player.name,
            player.uniqueId,
            banReason,
            banningAdmin,
            banTime,
            unbanTime,
            banDuration
        ).colored()
    }

    fun getBanFormat(
        player: OfflinePlayer,
        banReason: String = "",
        banDuration: String = "",
        banTime: String = "",
        unbanTime: String = "",
        banningAdmin: String = ""
    ): String {
        val banMessage =
            ConfigManager.config.getStringList("options.message-format.ban").joinToString(separator = "")
        return banMessage.replaceWithOrder(
            player.name!!,
            player.uniqueId,
            banReason,
            banningAdmin,
            banTime,
            unbanTime,
            banDuration
        ).colored()
    }

    fun getTimeFormat(): String = ConfigManager.config.getString("options.time-format.time")!!
}