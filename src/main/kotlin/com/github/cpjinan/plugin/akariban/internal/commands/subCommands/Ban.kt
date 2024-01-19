package com.github.cpjinan.plugin.akariban.internal.commands.subCommands

import com.github.cpjinan.plugin.akariban.internal.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akarilib.utils.TimeUtil.formatToString
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import java.time.LocalDateTime

object Ban {
    val ban = subCommand {
        createHelper()
        dynamic("player", optional = false).dynamic("duration", optional = false) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (Bukkit.getOfflinePlayer(context["player"]).isOnline) {
                    AkariBanAPI.banOnlinePlayer(
                        Bukkit.getPlayer(context["player"])!!,
                        ConfigManager.getDefaultBanReason(),
                        context["duration"],
                        LocalDateTime.now().formatToString(FormatManager.getTimeFormat()),
                        sender.name
                    )
                } else {
                    AkariBanAPI.banOfflinePlayer(
                        context["player"],
                        ConfigManager.getDefaultBanReason(),
                        context["duration"],
                        LocalDateTime.now().formatToString(FormatManager.getTimeFormat()),
                        sender.name
                    )
                }
            }
        }.dynamic("reason", optional = true) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (Bukkit.getOfflinePlayer(context["player"]).isOnline) {
                    AkariBanAPI.banOnlinePlayer(
                        Bukkit.getPlayer(context["player"])!!,
                        context["reason"],
                        context["duration"],
                        LocalDateTime.now().formatToString(FormatManager.getTimeFormat()),
                        sender.name
                    )
                } else {
                    AkariBanAPI.banOfflinePlayer(
                        context["player"],
                        context["reason"],
                        context["duration"],
                        LocalDateTime.now().formatToString(FormatManager.getTimeFormat()),
                        sender.name
                    )
                }
            }
        }
    }
}