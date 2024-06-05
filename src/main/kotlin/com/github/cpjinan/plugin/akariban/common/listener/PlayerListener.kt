package com.github.cpjinan.plugin.akariban.common.listener

import com.github.cpjinan.plugin.akariban.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akariban.internal.manager.DatabaseManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager.getPlayerID
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToLocalDateTime
import org.bukkit.Bukkit
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import taboolib.common.platform.event.SubscribeEvent
import java.time.LocalDateTime

object PlayerListener {
    @SubscribeEvent
    fun onPlayerJoin() {
        DatabaseManager.getDatabase().save()
    }

    @SubscribeEvent
    fun onPlayerLogin(event: AsyncPlayerPreLoginEvent) {
        // data
        val offlinePlayer = Bukkit.getOfflinePlayer(event.uniqueId)
        val db = DatabaseManager.getDatabase()
        val data = db.getPlayerByName(event.name.getPlayerID())
        db.save()
        // time
        if (data.unbanTime.isBlank()) {
            if (!ConfigManager.isEnabledWhitelist() || data.isWhitelisted) event.allow()
            else {
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    FormatManager.getWhitelistFormat(
                        offlinePlayer
                    )
                )
            }
        } else {
            val now = LocalDateTime.now()
            val unbanTime = data.unbanTime.formatToLocalDateTime(FormatManager.getTimeFormat())
            if (now.isAfter(unbanTime)) AkariBanAPI.unbanPlayer(event.name.getPlayerID())
            else if (data.isBanned) {
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
                    FormatManager.getBanFormat(
                        offlinePlayer,
                        data.banReason,
                        data.banDuration,
                        data.banTime,
                        data.unbanTime,
                        data.banningAdmin
                    )
                )
            }
        }
    }
}