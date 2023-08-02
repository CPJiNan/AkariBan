package com.github.cpjinan.command

import com.github.cpjinan.manager.ConfigManager
import com.github.cpjinan.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime

@CommandHeader(
    name = "ban",
    aliases = ["banex"],
    permission = "playerbanex.admin",
    permissionDefault = PermissionDefault.OP
)
object BanCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
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