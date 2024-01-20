package com.github.cpjinan.plugin.akariban.internal.commands.subCommands

import com.github.cpjinan.plugin.akariban.internal.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.PlayerManager.getPlayerID
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang

object Unban {
    val unban = subCommand {
        createHelper()
        dynamic("player", optional = false) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                AkariBanAPI.unbanPlayer(context["player"].getPlayerID())
                sender.sendLang("unban-success", context["player"])
            }
        }
    }
}