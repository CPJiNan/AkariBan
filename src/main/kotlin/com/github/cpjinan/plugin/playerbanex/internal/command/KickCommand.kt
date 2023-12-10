package com.github.cpjinan.plugin.playerbanex.internal.command

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
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
    name = "Kick",
    aliases = ["kickex","kick-ex"],
    permission = "playerbanex.admin",
    permissionDefault = PermissionDefault.OP
)
object KickCommand {
    @CommandBody
    val main = mainCommand {
        createHelper()
        dynamic("player") {
            suggestPlayers()
        }.dynamic("reason") {
            execute<ProxyCommandSender> { sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, _: String ->
                Bukkit.getPlayerExact(context["player"])?.kickPlayer(
                    FormatManager.getKickFormat(
                        context["player"],
                        context["reason"],
                        DateTimeFormatter.ofPattern(ConfigManager.options.getString("time-format"))
                            .format(LocalDateTime.now()),
                        sender.name
                    )
                )
                sender.sendLang("kick-success", context["player"])
                log["kick"] = log.getStringList("kick").plus(
                    "玩家" + context["player"] + "被" + sender.name + "于系统时间" + DateTimeFormatter.ofPattern(
                        ConfigManager.options.getString("time-format")
                    ).format(LocalDateTime.now()) + "因" + context["reason"] + "从服务器请出"
                )
                log.saveToFile(log.file)
                log.reload()
            }
        }
    }
}