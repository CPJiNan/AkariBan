package com.github.cpjinan.command

import com.github.cpjinan.manager.ConfigManager
import com.github.cpjinan.manager.ConfigManager.data
import com.github.cpjinan.manager.ConfigManager.database
import com.github.cpjinan.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.expansion.getDataContainer
import taboolib.expansion.setupDataContainer
import taboolib.module.chat.colored
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
                dynamic("player") {
                    suggestPlayers()
                }.dynamic("reason") {
                    execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                        Bukkit.getPlayerExact(context["player"])
                            ?.kickPlayer(FormatManager.getKickFormat(context["player"], context["reason"], LocalDateTime.now().toString()))
                        ConfigManager.data["logs.kick"] = ConfigManager.data.getStringList("logs.kick").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "因" + context["reason"] + "从服务器请出")
                        database.saveToFile(database.file)
                        sender.sendLang("kick-success", context["player"])
                    }
                }
            }
        }

        // 封禁模块
        if (ConfigManager.ban.getBoolean("enable")) {
            literal("ban") {
                dynamic("player").dynamic("reason") {
                    execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                        ConfigManager.data["players.ban"] = ConfigManager.data.getStringList("players.ban").plus(context["player"])
                        ConfigManager.data["logs.ban"] = ConfigManager.data.getStringList("logs.ban").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "因" + context["reason"] + "从服务器封禁")
                        database.saveToFile(database.file)
                        Bukkit.getPlayerExact(context["player"])?.setupDataContainer()
                        Bukkit.getPlayerExact(context["player"])?.getDataContainer()?.set("isBanned", true)
                        Bukkit.getPlayerExact(context["player"])?.getDataContainer()?.set("reason", context["reason"])
                        Bukkit.getPlayerExact(context["player"])?.getDataContainer()?.set("time", LocalDateTime.now().toString())
                        Bukkit.getPlayerExact(context["player"])?.kickPlayer(FormatManager.getBanFormat(context["player"], context["reason"], LocalDateTime.now().toString()))
                        sender.sendLang("ban-success", context["player"])
                    }
                }
            }
        }

        // 解封模块
        if (ConfigManager.ban.getBoolean("enable")) {
            literal("unban") {
                dynamic("player") {
                    execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                        val playerList = data.getStringList("players.ban").toMutableList()
                        playerList.removeIf { it == context["player"] }
                        data["players.ban"] = playerList.toList()
                        data["logs.ban"] = data.getStringList("logs.ban").plus( ( "玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "从白名单删除" ) )
                        database.saveToFile(database.file)
                        sender.sendLang("unban-success")
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
                            database.saveToFile(database.file)
                            Bukkit.getPlayerExact(context["player"])?.setupDataContainer()
                            Bukkit.getPlayerExact(context["player"])?.getDataContainer()?.set("isWhitelisted", true)
                            Bukkit.getPlayerExact(context["player"])?.kickPlayer(FormatManager.getWhitelistFormat(context["player"]))
                            sender.sendLang("whitelist-add-success", context["player"])
                        }
                    }
                }
                //删除白名单
                literal("remove") {
                    dynamic("player") {
                        execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                            val playerList = data.getStringList("players.whitelist").toMutableList()
                            playerList.removeIf { it == context["player"] }
                            data["players.whitelist"] = playerList.toList()
                            ConfigManager.data["logs.whitelist"] = ConfigManager.data.getStringList("logs.whitelist").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "从白名单删除")
                            database.saveToFile(database.file)
                            sender.sendLang("whitelist-remove-success", context["player"])
                        }
                    }
                }
            }
        }

    }

}