package com.github.cpjinan.command

import com.github.cpjinan.manager.ConfigManager
import com.github.cpjinan.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.chat.colored
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

@CommandHeader(
    name = "kick",
    aliases = ["kickex"],
)
object KickCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
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