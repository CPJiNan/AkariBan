package com.github.cpjinan.plugin.akariban.internal.command.subCommand

import com.github.cpjinan.plugin.akariban.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager.isPlayerOnline
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToString
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

@Suppress("DuplicatedCode")
object Ban {
    val ban = subCommand {
        createHelper()
        dynamic("player", optional = false) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                val playerName = context["player"]
                val banReason = ConfigManager.getDefaultBanReason()
                val banDuration = ConfigManager.getTimeFormat().getString("Permanent")!!
                val banTime = LocalDateTime.now().formatToString(FormatManager.getTimeFormat())
                val banningAdmin = sender.name
                if (playerName.isPlayerOnline()) {
                    AkariBanAPI.banOnlinePlayer(
                        Bukkit.getPlayer(playerName)!!,
                        banReason,
                        banDuration,
                        banTime,
                        banningAdmin
                    )
                } else {
                    AkariBanAPI.banOfflinePlayer(
                        playerName,
                        banReason,
                        banDuration,
                        banTime,
                        banningAdmin
                    )
                }
                sender.sendLang("Ban-Success", playerName)
            }
        }.dynamic("duration", optional = true) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                val playerName = context["player"]
                val banReason = ConfigManager.getDefaultBanReason()
                val banDuration = context["duration"]
                val banTime = LocalDateTime.now().formatToString(FormatManager.getTimeFormat())
                val banningAdmin = sender.name
                if (playerName.isPlayerOnline()) {
                    AkariBanAPI.banOnlinePlayer(
                        Bukkit.getPlayer(playerName)!!,
                        banReason,
                        banDuration,
                        banTime,
                        banningAdmin
                    )
                } else {
                    AkariBanAPI.banOfflinePlayer(
                        playerName,
                        banReason,
                        banDuration,
                        banTime,
                        banningAdmin
                    )
                }
                sender.sendLang("Ban-Success", playerName)
            }
        }.dynamic("reason", optional = true) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                val playerName = context["player"]
                val banReason = context["reason"]
                val banDuration = context["duration"]
                val banTime = LocalDateTime.now().formatToString(FormatManager.getTimeFormat())
                val banningAdmin = sender.name
                if (playerName.isPlayerOnline()) {
                    AkariBanAPI.banOnlinePlayer(
                        Bukkit.getPlayer(context["player"])!!,
                        banReason,
                        banDuration,
                        banTime,
                        banningAdmin
                    )
                } else {
                    AkariBanAPI.banOfflinePlayer(
                        playerName,
                        banReason,
                        banDuration,
                        banTime,
                        banningAdmin
                    )
                }
                sender.sendLang("Ban-Success", context["player"])
            }
        }
    }
}