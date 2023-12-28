package com.github.cpjinan.plugin.playerbanex.internal.command.subcommand

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
import com.github.cpjinan.plugin.playerbanex.internal.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Ban {
    val banCommand = mainCommand {
        createHelper()
        dynamic("player") {}.dynamic("period").dynamic("reason") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (sender.isOp || sender.hasPermission("playerbanex.admin") || sender.hasPermission("playerbanex.ban")) {
                    Bukkit.getPlayerExact(context["player"])?.kickPlayer(
                        FormatManager.getBanFormat(
                            context["player"],
                            context["period"],
                            context["reason"],
                            DateTimeFormatter.ofPattern(ConfigManager.options.getString("time-format"))
                                .format(LocalDateTime.now()),
                            sender.name
                        )
                    )
                    ConfigManager.banlist["${context["player"]}.enable"] = true
                    ConfigManager.banlist["${context["player"]}.time"] =
                        DateTimeFormatter.ofPattern(ConfigManager.options.getString("time-format"))
                            .format(LocalDateTime.now())
                    ConfigManager.banlist["${context["player"]}.period"] = context["period"]
                    ConfigManager.banlist["${context["player"]}.reason"] = context["reason"]
                    ConfigManager.banlist["${context["player"]}.admin"] = sender.name
                    ConfigManager.data.saveToFile(ConfigManager.data.file)
                    ConfigManager.data.reload()
                    sender.sendLang("ban-success", context["player"])
                    ConfigManager.log["ban"] = ConfigManager.log.getStringList("ban").plus(
                        "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                            ConfigManager.options.getString("time-format")
                        ).format(LocalDateTime.now()) + "因" + context["reason"] + "从服务器封禁至" + context["period"]
                    )
                    ConfigManager.log.saveToFile(ConfigManager.log.file)
                    ConfigManager.log.reload()
                } else sender.sendLang("no-permission")
            }
        }
    }
    val ban = subCommand {
        createHelper()
        dynamic("player") {}.dynamic("period").dynamic("reason") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                if (sender.isOp || sender.hasPermission("playerbanex.admin") || sender.hasPermission("playerbanex.ban")) {
                    Bukkit.getPlayerExact(context["player"])?.kickPlayer(
                        FormatManager.getBanFormat(
                            context["player"],
                            context["period"],
                            context["reason"],
                            DateTimeFormatter.ofPattern(ConfigManager.options.getString("time-format"))
                                .format(LocalDateTime.now()),
                            sender.name
                        )
                    )
                    ConfigManager.banlist["${context["player"]}.enable"] = true
                    ConfigManager.banlist["${context["player"]}.time"] =
                        DateTimeFormatter.ofPattern(ConfigManager.options.getString("time-format"))
                            .format(LocalDateTime.now())
                    ConfigManager.banlist["${context["player"]}.period"] = context["period"]
                    ConfigManager.banlist["${context["player"]}.reason"] = context["reason"]
                    ConfigManager.banlist["${context["player"]}.admin"] = sender.name
                    ConfigManager.data.saveToFile(ConfigManager.data.file)
                    ConfigManager.data.reload()
                    sender.sendLang("ban-success", context["player"])
                    ConfigManager.log["ban"] = ConfigManager.log.getStringList("ban").plus(
                        "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                            ConfigManager.options.getString("time-format")
                        ).format(LocalDateTime.now()) + "因" + context["reason"] + "从服务器封禁至" + context["period"]
                    )
                    ConfigManager.log.saveToFile(ConfigManager.log.file)
                    ConfigManager.log.reload()
                } else sender.sendLang("no-permission")
            }
        }
    }
}