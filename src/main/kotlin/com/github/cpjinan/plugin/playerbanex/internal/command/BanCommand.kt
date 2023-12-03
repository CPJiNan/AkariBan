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
    name = "Ban",
    aliases = ["banex","ban-ex"],
    permission = "playerbanex.admin",
    permissionDefault = PermissionDefault.OP
)
object BanCommand {
    @CommandBody
    val main = mainCommand {
        createHelper()
        dynamic("player") {}.dynamic("period").dynamic("reason") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
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
                banlist["${context["player"]}.enable"] = true
                banlist["${context["player"]}.time"] =
                    DateTimeFormatter.ofPattern(ConfigManager.options.getString("time-format"))
                        .format(LocalDateTime.now())
                banlist["${context["player"]}.period"] = context["period"]
                banlist["${context["player"]}.reason"] = context["reason"]
                banlist["${context["player"]}.admin"] = sender.name
                data.saveToFile(data.file)
                data.reload()
                sender.sendLang("ban-success", context["player"])
                log["ban"] = log.getStringList("ban").plus(
                    "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                        ConfigManager.options.getString("time-format")
                    ).format(LocalDateTime.now()) + "因" + context["reason"] + "从服务器封禁至" + context["period"]
                )
                log.saveToFile(log.file)
                log.reload()
            }
        }
    }
}