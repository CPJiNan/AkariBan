package com.github.cpjinan.plugin.playerbanex.internal.command.subcommand

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Unban {
    val unbanCommand = mainCommand {
        createHelper()
        dynamic("player") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (sender.isOp || sender.hasPermission("playerbanex.admin") || sender.hasPermission("playerbanex.unban")) {
                    ConfigManager.banlist["${context["player"]}.enable"] = false
                    ConfigManager.data.saveToFile(ConfigManager.data.file)
                    ConfigManager.data.reload()
                    sender.sendLang("unban-success", context["player"])
                    ConfigManager.log["unban"] = ConfigManager.log.getStringList("unban").plus(
                        "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                            ConfigManager.options.getString("time-format")
                        ).format(LocalDateTime.now()) + "从服务器解封"
                    )
                    ConfigManager.log.saveToFile(ConfigManager.log.file)
                    ConfigManager.log.reload()
                } else sender.sendLang("no-permission")
            }
        }
    }
    val unban = subCommand {
        createHelper()
        dynamic("player") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (sender.isOp || sender.hasPermission("playerbanex.admin") || sender.hasPermission("playerbanex.unban")) {
                    ConfigManager.banlist["${context["player"]}.enable"] = false
                    ConfigManager.data.saveToFile(ConfigManager.data.file)
                    ConfigManager.data.reload()
                    sender.sendLang("unban-success", context["player"])
                    ConfigManager.log["unban"] = ConfigManager.log.getStringList("unban").plus(
                        "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                            ConfigManager.options.getString("time-format")
                        ).format(LocalDateTime.now()) + "从服务器解封"
                    )
                    ConfigManager.log.saveToFile(ConfigManager.log.file)
                    ConfigManager.log.reload()
                } else sender.sendLang("no-permission")
            }
        }
    }
}