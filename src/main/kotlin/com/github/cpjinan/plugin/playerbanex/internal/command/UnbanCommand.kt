package com.github.cpjinan.plugin.playerbanex.internal.command

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager.banlist
import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager.data
import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager.log
import com.github.cpjinan.plugin.playerbanex.internal.manager.FormatManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@CommandHeader(
    name = "Unban",
    aliases = ["unbanex","unban-ex","pardon","pardonex","pardon-ex"],
    permission = "playerbanex.admin",
    permissionDefault = PermissionDefault.OP
)
object UnbanCommand {
    @CommandBody
    val main = mainCommand {
        createHelper()
        dynamic("player") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                banlist["${context["player"]}.enable"] = false
                data.saveToFile(data.file)
                data.reload()
                sender.sendLang("unban-success", context["player"])
                log["unban"] = log.getStringList("unban").plus(
                    "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                        ConfigManager.options.getString("time-format")
                    ).format(LocalDateTime.now()) + "从服务器解封"
                )
                log.saveToFile(log.file)
                log.reload()
            }
        }
    }
}