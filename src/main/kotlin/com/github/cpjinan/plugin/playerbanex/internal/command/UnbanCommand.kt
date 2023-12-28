package com.github.cpjinan.plugin.playerbanex.internal.command

import com.github.cpjinan.plugin.playerbanex.internal.command.subcommand.Unban
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault

@CommandHeader(
    name = "Unban",
    aliases = ["unban-ex","pardon","pardon-ex"],
    permissionDefault = PermissionDefault.OP
)
object UnbanCommand {
    @CommandBody
    val main = Unban.unbanCommand
}