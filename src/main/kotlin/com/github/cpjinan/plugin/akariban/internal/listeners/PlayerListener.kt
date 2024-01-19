package com.github.cpjinan.plugin.akariban.internal.listeners

import com.github.cpjinan.plugin.akariban.internal.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.DatabaseManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akariban.internal.manager.PlayerManager.getPlayerID
import com.github.cpjinan.plugin.akarilib.utils.TimeUtil.formatToLocalDateTime
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
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
        val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(event.uniqueId)
        val db = DatabaseManager.getDatabase()
        val data = db.getPlayerByName(offlinePlayer.getPlayerID()!!)
        // time
        if (LocalDateTime.now()
                .isAfter(data.unbanTime.formatToLocalDateTime(FormatManager.getTimeFormat()))
        ) AkariBanAPI.unbanPlayer(
            offlinePlayer.getPlayerID()!!
        )
        // kick
        if (data.isBanned) event.disallow(
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