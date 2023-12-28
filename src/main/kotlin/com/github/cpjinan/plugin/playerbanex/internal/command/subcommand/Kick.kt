package com.github.cpjinan.plugin.playerbanex.internal.command.subcommand

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
import com.github.cpjinan.plugin.playerbanex.internal.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Kick {
    val kickCommand = mainCommand {
        createHelper()
        dynamic("player") {
            suggestPlayers()
        }.dynamic("reason") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (sender.isOp || sender.hasPermission("playerbanex.admin") || sender.hasPermission("playerbanex.kick")) {
                    Bukkit.getPlayerExact(context["player"])?.kickPlayer(
                        FormatManager.getKickFormat(
                            context["player"], context["reason"], DateTimeFormatter.ofPattern(
                                ConfigManager.options.getString("time-format")
                            ).format(LocalDateTime.now()), sender.name
                        )
                    )
                    sender.sendLang("kick-success", context["player"])
                    ConfigManager.log["kick"] = ConfigManager.log.getStringList("kick").plus(
                        "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                            ConfigManager.options.getString("time-format")
                        ).format(LocalDateTime.now()) + "因" + context["reason"] + "从服务器请出"
                    )
                    ConfigManager.log.saveToFile(ConfigManager.log.file)
                    ConfigManager.log.reload()
                } else sender.sendLang("no-permission")
            }
        }
    }
    val kick = subCommand {
        createHelper()
        dynamic("player") {
            suggestPlayers()
        }.dynamic("reason") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (sender.isOp || sender.hasPermission("playerbanex.admin") || sender.hasPermission("playerbanex.kick")) {
                    Bukkit.getPlayerExact(context["player"])?.kickPlayer(
                        FormatManager.getKickFormat(
                            context["player"], context["reason"], DateTimeFormatter.ofPattern(
                                ConfigManager.options.getString("time-format")
                            ).format(LocalDateTime.now()), sender.name
                        )
                    )
                    sender.sendLang("kick-success", context["player"])
                    ConfigManager.log["kick"] = ConfigManager.log.getStringList("kick").plus(
                        "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                            ConfigManager.options.getString("time-format")
                        ).format(LocalDateTime.now()) + "因" + context["reason"] + "从服务器请出"
                    )
                    ConfigManager.log.saveToFile(ConfigManager.log.file)
                    ConfigManager.log.reload()
                } else sender.sendLang("no-permission")
            }
        }
    }
}