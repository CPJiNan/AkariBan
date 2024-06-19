package com.github.cpjinan.plugin.akariban.internal.command

import com.github.cpjinan.plugin.akariban.internal.command.subCommand.Ban
import com.github.cpjinan.plugin.akariban.internal.command.subCommand.Kick
import com.github.cpjinan.plugin.akariban.internal.command.subCommand.Unban
import com.github.cpjinan.plugin.akariban.internal.command.subCommand.Whitelist
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.lang.sendLang

@CommandHeader(name = "akariban", permissionDefault = PermissionDefault.OP)
object MainCommand {
    @CommandBody
    val main = mainCommand { createHelper() }

    @CommandBody
    val kick = Kick.kick

    @CommandBody
    val ban = Ban.ban

    @CommandBody
    val unban = Unban.unban

    @CommandBody
    val whitelist = Whitelist.whitelist

    @CommandBody(hidden = true)
    val help = mainCommand { createHelper() }

    @CommandBody
    val reload = subCommand {
        execute { sender: ProxyCommandSender, _: CommandContext<ProxyCommandSender>, _: String ->
            ConfigManager.settings.reload()
            sender.sendLang("Plugin-Reloaded")
        }
    }
}