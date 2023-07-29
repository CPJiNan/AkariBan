package com.github.cpjinan.command

import com.github.cpjinan.manager.ConfigManager
import com.github.cpjinan.manager.ConfigManager.data
import com.github.cpjinan.manager.ConfigManager.database
import com.github.cpjinan.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

@CommandHeader(
    name = "PlayerBanEX",
    aliases = ["playerban"],
)
object MainCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()

        // 请出模块
        if (ConfigManager.kick.getBoolean("enable")) {
            literal("kick") {
                dynamic("player"){
                    suggestPlayers()
                }.dynamic("reason") {
                    execute<ProxyCommandSender>{ sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                        if (ConfigManager.kick.getBoolean("enable")) {
                            Bukkit.getPlayerExact(context["player"])?.kickPlayer(FormatManager.getKickFormat(context["player"], context["reason"], LocalDateTime.now().toString()))
                            ConfigManager.data["logs.kick"] = ConfigManager.data.getStringList("logs.kick").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "因" + context["reason"] + "从服务器请出")
                            ConfigManager.database.saveToFile(ConfigManager.database.file)
                            sender.sendLang("kick-success", context["player"])
                        } else sender.sendLang("module-disabled")
                    }
                }
            }
        }

        // 封禁模块
        if (ConfigManager.ban.getBoolean("enable")) {
            literal("ban") {
                dynamic("player").dynamic("reason") {
                    execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                        if (ConfigManager.ban.getBoolean("enable")) {
                            ConfigManager.data["players.ban"] = ConfigManager.data.getStringList("players.ban").plus(context["player"])
                            ConfigManager.data["logs.ban"] = ConfigManager.data.getStringList("logs.ban").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "因" + context["reason"] + "从服务器封禁")
                            ConfigManager.database.saveToFile(ConfigManager.database.file)
                            Bukkit.getPlayerExact(context["player"])?.kickPlayer(FormatManager.getBanFormat(context["player"], context["reason"], LocalDateTime.now().toString()))
                            sender.sendLang("ban-success", context["player"])
                        }else sender.sendLang("module-disabled")
                    }
                }
            }
        }

        // 解封模块
        if (ConfigManager.ban.getBoolean("enable")) {
            literal("unban") {
                dynamic("player") {
                    execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                        if (ConfigManager.ban.getBoolean("enable")) {
                            val playerList = ConfigManager.data.getStringList("players.ban").toMutableList()
                            playerList.removeIf { it == context["player"] }
                            ConfigManager.data["players.ban"] = playerList.toList()
                            ConfigManager.data["logs.ban"] = ConfigManager.data.getStringList("logs.ban").plus(("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "从白名单删除"))
                            ConfigManager.database.saveToFile(ConfigManager.database.file)
                            sender.sendLang("unban-success", context["player"])
                        }
                    }
                }
            }
        }

        // 白名单模块
        if (ConfigManager.whitelist.getBoolean("enable")) {
            literal("whitelist") {
                // 添加白名单
                literal("add") {
                    dynamic("player") {
                        execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                            ConfigManager.data["players.whitelist"] = ConfigManager.data.getStringList("players.whitelist").plus(context["player"])
                            ConfigManager.data["logs.whitelist"] = ConfigManager.data.getStringList("logs.whitelist").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "添加至白名单")
                            ConfigManager.database.saveToFile(ConfigManager.database.file)
                            sender.sendLang("whitelist-add-success", context["player"])
                        }
                    }
                }
                //删除白名单
                literal("add") {
                    dynamic("player") {
                        execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                            val playerList = ConfigManager.data.getStringList("players.whitelist").toMutableList()
                            playerList.removeIf { it == context["player"] }
                            ConfigManager.data["players.whitelist"] = playerList.toList()
                            ConfigManager.data["logs.whitelist"] = ConfigManager.data.getStringList("logs.whitelist").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "从白名单删除")
                            ConfigManager.database.saveToFile(ConfigManager.database.file)
                            Bukkit.getPlayerExact(context["player"])?.kickPlayer(FormatManager.getWhitelistFormat(context["player"]))
                            sender.sendLang("whitelist-remove-success", context["player"])
                        }
                    }
                }
            }
        }

    }

}