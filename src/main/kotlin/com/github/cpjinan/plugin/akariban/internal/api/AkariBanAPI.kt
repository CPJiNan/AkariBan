package com.github.cpjinan.plugin.akariban.internal.api

import com.github.cpjinan.plugin.akariban.internal.events.ban.OfflinePlayerBanEvent
import com.github.cpjinan.plugin.akariban.internal.events.ban.OnlinePlayerBanEvent
import com.github.cpjinan.plugin.akariban.internal.events.kick.PlayerKickEvent
import com.github.cpjinan.plugin.akariban.internal.events.unban.PlayerUnbanEvent
import com.github.cpjinan.plugin.akariban.internal.manager.DatabaseManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akariban.internal.manager.PlayerManager.getPlayerID
import com.github.cpjinan.plugin.akariban.internal.manager.TimeManager
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

object AkariBanAPI {
    private fun kick(
        player: Player,
        kickReason: String = "",
        kickTime: String = "",
        kickingAdmin: String = ""
    ) {
        // player kick event
        val playerKickEvent = PlayerKickEvent(player, kickReason, kickTime, kickingAdmin)
        playerKickEvent.call()
        if (playerKickEvent.isCancelled) return
        // kick message
        val kickMessage = FormatManager.getKickFormat(
            playerKickEvent.player,
            playerKickEvent.kickReason,
            playerKickEvent.kickTime,
            playerKickEvent.kickingAdmin
        )
        // kick player
        player.kickPlayer(kickMessage)
    }

    private fun onlineBan(
        player: Player,
        banReason: String = "",
        banDuration: String = "",
        banTime: String = "",
        banningAdmin: String = ""
    ) {
        // online player ban event
        val onlinePlayerBanEvent = OnlinePlayerBanEvent(player, banReason, banDuration, banTime, banningAdmin)
        onlinePlayerBanEvent.call()
        if (onlinePlayerBanEvent.isCancelled) return
        // set data
        setPlayerData(
            playerID = onlinePlayerBanEvent.player.getPlayerID(),
            isBanned = true,
            banReason = onlinePlayerBanEvent.banReason,
            banningAdmin = onlinePlayerBanEvent.banningAdmin,
            banTime = onlinePlayerBanEvent.banTime,
            banDuration = onlinePlayerBanEvent.banDuration
        )
        // ban message
        val banMessage = FormatManager.getBanFormat(
            onlinePlayerBanEvent.player,
            onlinePlayerBanEvent.banReason,
            onlinePlayerBanEvent.banDuration,
            onlinePlayerBanEvent.banTime,
            TimeManager.getUnbanTime(onlinePlayerBanEvent.banTime, onlinePlayerBanEvent.banDuration),
            onlinePlayerBanEvent.banningAdmin
        )
        // kick player
        player.kickPlayer(banMessage)
    }

    private fun offlineBan(
        playerName: String = "",
        banReason: String = "",
        banDuration: String = "",
        banTime: String = "",
        banningAdmin: String = ""
    ) {
        // offline player
        val offlinePlayer: OfflinePlayer = Bukkit.getOfflinePlayer(playerName)
        // offline player ban event
        val offlinePlayerBanEvent = OfflinePlayerBanEvent(playerName, banReason, banDuration, banTime, banningAdmin)
        offlinePlayerBanEvent.call()
        if (offlinePlayerBanEvent.isCancelled) return
        // set data
        setPlayerData(
            playerID = Bukkit.getOfflinePlayer(offlinePlayerBanEvent.playerName).getPlayerID()!!,
            isBanned = true,
            banReason = offlinePlayerBanEvent.banReason,
            banningAdmin = offlinePlayerBanEvent.banningAdmin,
            banTime = offlinePlayerBanEvent.banTime,
            banDuration = offlinePlayerBanEvent.banDuration
        )
    }

    private fun unban(
        playerID: String,
    ) {
        // player unban event
        val playerUnbanEvent = PlayerUnbanEvent(playerID)
        playerUnbanEvent.call()
        if (playerUnbanEvent.isCancelled) return
        // set data
        setPlayerData(playerID = playerID)
    }

    private fun setPlayerData(
        playerID: String,
        isBanned: Boolean = false,
        banReason: String = "",
        banningAdmin: String = "",
        banTime: String = "",
        banDuration: String = ""
    ) {
        val db = DatabaseManager.getDatabase()
        val data = db.getPlayerByName(playerID)
        data.playerID = playerID
        data.isBanned = isBanned
        data.banTime = banTime
        data.unbanTime = TimeManager.getUnbanTime(banTime, banDuration)
        data.banDuration = banDuration
        data.banReason = banReason
        data.banningAdmin = banningAdmin
        db.updatePlayer(playerID, data)
    }

    fun kickPlayer(player: Player, kickReason: String, kickTime: String, kickingAdmin: String) {
        kick(player, kickReason, kickTime, kickingAdmin)
    }

    fun banOnlinePlayer(
        player: Player,
        banReason: String = "",
        banDuration: String = "",
        banTime: String = "",
        banningAdmin: String = ""
    ) {
        onlineBan(player, banReason, banDuration, banTime, banningAdmin)
    }

    fun banOfflinePlayer(
        playerName: String = "",
        banReason: String = "",
        banDuration: String = "",
        banTime: String = "",
        banningAdmin: String = ""
    ) {
        offlineBan(playerName, banReason, banDuration, banTime, banningAdmin)
    }

    fun unbanPlayer(
        playerID: String
    ) {
        unban(playerID)
    }
}