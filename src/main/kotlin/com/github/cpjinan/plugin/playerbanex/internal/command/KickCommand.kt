package com.github.cpjinan.plugin.playerbanex.internal.command

import com.github.cpjinan.plugin.playerbanex.internal.command.subcommand.Kick
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault

@CommandHeader(
    name = "Kick",
    aliases = ["kick-ex"],
    permissionDefault = PermissionDefault.OP
)
object KickCommand {
    @CommandBody
    val main = Kick.kickCommand
}