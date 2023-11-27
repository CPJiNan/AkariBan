package com.github.cpjinan.plugin.playerbanex.internal.listener

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager.banlist
import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager.data
import com.github.cpjinan.plugin.playerbanex.internal.manager.FormatManager
import org.bukkit.Bukkit
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import taboolib.common.platform.event.SubscribeEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LoginEvent {

    @SubscribeEvent
    fun onPlayerLogin(event : AsyncPlayerPreLoginEvent) {
        if (banlist.getBoolean("${event.name}.enable") && LocalDateTime.parse(ConfigManager.banlist.getString(event.name+".period"), DateTimeFormatter.ofPattern(ConfigManager.options.getString("period-format"))).isAfter(LocalDateTime.now()))
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, FormatManager.getBanFormat(event.name, banlist.getString("${event.name}.period")!!,banlist.getString("${event.name}.reason")!!,banlist.getString("${event.name}.time")!!,banlist.getString("${event.name}.admin")!!))
        else {
            banlist["${event.name}.enable"] = false
            data.saveToFile(data.file)
            data.reload()
        }
    }

}