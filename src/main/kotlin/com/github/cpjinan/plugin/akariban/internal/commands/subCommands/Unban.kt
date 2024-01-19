package com.github.cpjinan.plugin.akariban.internal.commands.subCommands

import com.github.cpjinan.plugin.akariban.internal.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.PlayerManager.getPlayerID
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper

object Unban {
    val kick = subCommand {
        createHelper()
        dynamic("player", optional = false) {
            execute<ProxyCommandSender> { _: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                AkariBanAPI.unbanPlayer(Bukkit.getOfflinePlayer(context["player"]).getPlayerID()!!)
            }
        }
    }
}