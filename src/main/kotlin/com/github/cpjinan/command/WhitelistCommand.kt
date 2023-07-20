package com.github.cpjinan.command

import com.github.cpjinan.manager.ConfigManager
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
    name = "whitelist",
    aliases = ["whitelistex"],
)
object WhitelistCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
        if (ConfigManager.whitelist.getBoolean("enable")) {
            literal("whitelist") {
                // 添加白名单
                literal("add") {
                    dynamic("player") {
                        execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                            ConfigManager.data["players.whitelist"] = ConfigManager.data.getStringList("players.whitelist").plus(context["player"])
                            ConfigManager.data["logs.whitelist"] = ConfigManager.data.getStringList("logs.whitelist").plus("玩家" + context["player"] + "被" + sender.name + "于系统时间" + LocalDateTime.now().toString() + "添加至白名单")
                            ConfigManager.database.saveToFile(ConfigManager.database.file)
                            Bukkit.getPlayerExact(context["player"])?.setupDataContainer()
                            Bukkit.getPlayerExact(context["player"])?.getDataContainer()?.set("isWhitelisted", true)
                            Bukkit.getPlayerExact(context["player"])?.kickPlayer(FormatManager.getWhitelistFormat(context["player"]))
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
                            sender.sendLang("whitelist-remove-success", context["player"])
                        }
                    }
                }
            }
        }
    }

}