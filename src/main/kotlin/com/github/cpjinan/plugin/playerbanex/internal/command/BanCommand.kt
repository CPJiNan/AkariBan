package com.github.cpjinan.plugin.playerbanex.internal.command

import com.github.cpjinan.plugin.playerbanex.internal.command.subcommand.Ban
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault

@CommandHeader(
    name = "Ban",
    aliases = ["ban-ex"],
    permissionDefault = PermissionDefault.OP
)
object BanCommand {
    @CommandBody
    val main = Ban.banCommand
}