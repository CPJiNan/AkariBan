package com.github.cpjinan.command

import com.github.cpjinan.manager.ConfigManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

@CommandHeader(
    name = "unban",
    aliases = ["unbanex"],
)
object UnbanCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
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