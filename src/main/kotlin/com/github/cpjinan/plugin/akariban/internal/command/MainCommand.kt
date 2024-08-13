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

    @CommandBody(permission = "akariban.kick")
    val kick = Kick.kick

    @CommandBody(permission = "akariban.ban")
    val ban = Ban.ban

    @CommandBody(permission = "akariban.unban")
    val unban = Unban.unban

    @CommandBody(permission = "akariban.whitelist")
    val whitelist = Whitelist.whitelist

    @CommandBody(hidden = true)
    val help = mainCommand { createHelper() }

    @CommandBody(permission = "akariban.reload")
    val reload = subCommand {
        execute { sender: ProxyCommandSender, _: CommandContext<ProxyCommandSender>, _: String ->
            ConfigManager.settings.reload()
            sender.sendLang("Plugin-Reloaded")
        }
    }

    fun simpleCommand() {
        simpleCommand("kick") { sender, args ->
            sender.performCommand("akariban kick ${args.joinToString(" ")}")
        }
        simpleCommand("ban") { sender, args ->
            sender.performCommand("akariban ban ${args.joinToString(" ")}")
        }
        simpleCommand("unban") { sender, args ->
            sender.performCommand("akariban unban ${args.joinToString(" ")}")
        }
        simpleCommand("whitelist") { sender, args ->
            sender.performCommand("akariban whitelist ${args.joinToString(" ")}")
        }
    }
}