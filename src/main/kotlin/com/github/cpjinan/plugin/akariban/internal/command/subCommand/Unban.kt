package com.github.cpjinan.plugin.akariban.internal.command.subCommand

import com.github.cpjinan.plugin.akariban.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager.getPlayerID
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
                sender.sendLang("Unban-Success", context["player"])
            }
        }
    }
}