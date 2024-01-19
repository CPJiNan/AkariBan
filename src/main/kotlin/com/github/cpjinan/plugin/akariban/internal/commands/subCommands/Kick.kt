package com.github.cpjinan.plugin.akariban.internal.commands.subCommands

import com.github.cpjinan.plugin.akariban.internal.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akarilib.utils.TimeUtil.formatToString
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.expansion.createHelper
import java.time.LocalDateTime

object Kick {
    val kick = subCommand {
        createHelper()
        dynamic("player", optional = false) {
            suggestPlayers()
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                AkariBanAPI.kickPlayer(
                    Bukkit.getPlayer(context["player"])!!,
                    ConfigManager.getDefaultKickReason(),
                    LocalDateTime.now().formatToString(FormatManager.getTimeFormat()),
                    sender.name
                )
            }
        }.dynamic("reason", optional = true) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                AkariBanAPI.kickPlayer(
                    Bukkit.getPlayer(context["player"])!!,
                    context["reason"],
                    LocalDateTime.now().formatToString(FormatManager.getTimeFormat()),
                    sender.name
                )
            }
        }
    }
}