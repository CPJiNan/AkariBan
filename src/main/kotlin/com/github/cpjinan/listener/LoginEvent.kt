package com.github.cpjinan.listener

import com.github.cpjinan.manager.ConfigManager
import com.github.cpjinan.manager.FormatManager
import org.bukkit.Bukkit
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.getDataContainer
import taboolib.expansion.setupDataContainer
import java.time.LocalDateTime

object LoginEvent {

    @SubscribeEvent
    fun onPlayerLogin(event : AsyncPlayerPreLoginEvent) {

        // 封禁模块
       if(ConfigManager.ban.getBoolean("enable")){
           if(ConfigManager.ban.getString("mode").equals("kick")) {
               Bukkit.getPlayerExact(event.name)?.setupDataContainer()
               if(Bukkit.getPlayerExact(event.name)?.getDataContainer()?.get("isBanned").toBoolean() && (ConfigManager.data.getList("players.ban")?.contains(event.name) == true)) Bukkit.getPlayerExact(event.name)?.kickPlayer(FormatManager.getBanFormat(event.name, Bukkit.getPlayerExact(event.name)?.getDataContainer()?.get("reason").toString(), Bukkit.getPlayerExact(event.name)?.getDataContainer()?.get("time").toString()))
               else {
                   Bukkit.getPlayerExact(event.name)?.getDataContainer()?.set("isBanned", false)
               }
           }else if(ConfigManager.ban.getString("mode").equals("disconnect") && (ConfigManager.data.getList("players.ban")?.contains(event.name) == true)) event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,FormatManager.getBanFormat(event.name,ConfigManager.ban.getString("placeholder.unknown-reason").toString(),ConfigManager.ban.getString("placeholder.unknown-time").toString()))
       }

        // 白名单模块
        if(ConfigManager.whitelist.getBoolean("enable")){
            if(ConfigManager.whitelist.getString("mode").equals("kick")) {
                Bukkit.getPlayerExact(event.name)?.setupDataContainer()
                if((!Bukkit.getPlayerExact(event.name)?.getDataContainer()?.get("isWhitelisted").toBoolean()) || (ConfigManager.data.getList("players.whitelist")?.contains(event.name) == false)) Bukkit.getPlayerExact(event.name)?.kickPlayer(FormatManager.getWhitelistFormat(event.name))
                else if((Bukkit.getPlayerExact(event.name)?.getDataContainer()?.get("isWhitelisted").toBoolean()) && (ConfigManager.data.getList("players.whitelist")?.contains(event.name) == false)) {
                    Bukkit.getPlayerExact(event.name)?.getDataContainer()?.set("isWhitelisted", false)
                }
            }else if(ConfigManager.whitelist.getString("mode").equals("disconnect") && (ConfigManager.data.getList("players.whitelist")?.contains(event.name) == false)) event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,FormatManager.getWhitelistFormat(event.name))
        }

    }

}