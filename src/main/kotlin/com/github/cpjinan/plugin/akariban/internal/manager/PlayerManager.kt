package com.github.cpjinan.plugin.akariban.internal.manager

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

@Suppress("DEPRECATION")
object PlayerManager {
    fun Player.getPlayerID(): String {
        return if (ConfigManager.config.getString("options.player-id").equals("name")) this.name
        else this.uniqueId.toString()
    }

    fun OfflinePlayer.getPlayerID(): String? {
        return if (ConfigManager.config.getString("options.player-id").equals("name")) this.name
        else this.uniqueId.toString()
    }

    fun String.getPlayerID(): String {
        return Bukkit.getOfflinePlayer(this).getPlayerID() ?: this
    }

    fun String.isPlayerOnline(): Boolean {
        return Bukkit.getOfflinePlayer(this).isOnline
    }
}