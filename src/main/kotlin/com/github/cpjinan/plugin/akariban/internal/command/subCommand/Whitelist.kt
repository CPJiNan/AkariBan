package com.github.cpjinan.plugin.akariban.internal.command.subCommand

import com.github.cpjinan.plugin.akariban.api.AkariBanAPI
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager
import com.github.cpjinan.plugin.akariban.internal.manager.FormatManager.getPlayerID
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToString
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.bool
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

object Whitelist {
    val whitelist = subCommand {
        createHelper()
        dynamic("player", optional = false).bool("isWhitelisted", optional = false) {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                val isWhitelisted = context["isWhitelisted"]
                val whitelistTime = LocalDateTime.now().formatToString(FormatManager.getTimeFormat())
                val whitelistingAdmin = sender.name
                AkariBanAPI.whitelistPlayer(
                    context["player"].getPlayerID(),
                    isWhitelisted.toBoolean(),
                    whitelistTime,
                    whitelistingAdmin
                )
                sender.sendLang(
                    when (isWhitelisted.toBoolean()) {
                        true -> "Whitelist-Success"
                        false -> "Remove-Whitelist-Success"
                    },
                    context["player"]
                )
            }
        }
    }
}