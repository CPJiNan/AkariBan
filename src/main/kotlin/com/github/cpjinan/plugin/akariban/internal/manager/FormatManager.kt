package com.github.cpjinan.plugin.akariban.internal.manager

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored

@Suppress("DEPRECATION")
object FormatManager {
    fun getKickFormat(
        player: Player,
        kickReason: String = "",
        kickTime: String = "",
        kickingAdmin: String = ""
    ): String {
        val kickMessage =
            ConfigManager.getKickFormat().joinToString(separator = "")
        return kickMessage.replaceWithOrder(player.name, player.uniqueId, kickReason, kickingAdmin, kickTime).colored()
    }

    fun getWhitelistFormat(
        player: OfflinePlayer
    ): String {
        val whitelistMessage =
            ConfigManager.getWhitelistFormat().joinToString(separator = "")
        return whitelistMessage.replaceWithOrder(player.name!!, player.uniqueId).colored()
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
            ConfigManager.getBanFormat().joinToString(separator = "")
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
            ConfigManager.getBanFormat().joinToString(separator = "")
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

    fun getTimeFormat(): String = ConfigManager.getTimeFormat().getString("Time")!!

    fun Player.getPlayerID(): String {
        return if (ConfigManager.getPlayerID().equals("name")) this.name
        else this.uniqueId.toString()
    }

    fun OfflinePlayer.getPlayerID(): String? {
        return if (ConfigManager.getPlayerID().equals("name")) this.name
        else this.uniqueId.toString()
    }

    fun String.getPlayerID(): String {
        return Bukkit.getOfflinePlayer(this).getPlayerID() ?: this
    }

    fun String.isPlayerOnline(): Boolean {
        return Bukkit.getOfflinePlayer(this).isOnline
    }
}