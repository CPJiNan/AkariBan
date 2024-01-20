package com.github.cpjinan.plugin.akariban.internal.commands.subCommands

import com.github.cpjinan.plugin.akariban.internal.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akariban.internal.manager.PlayerManager.isPlayerOnline
import com.github.cpjinan.plugin.akarilib.utils.TimeUtil.formatToString
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

object Ban {
    val ban = subCommand {
        createHelper()
        dynamic("player", optional = false).dynamic("duration", optional = false) {
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
                sender.sendLang("ban-success", playerName)
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
                sender.sendLang("ban-success", context["player"])
            }
        }
    }
}