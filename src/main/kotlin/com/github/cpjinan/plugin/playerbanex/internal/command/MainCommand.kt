package com.github.cpjinan.plugin.playerbanex.internal.command

import com.github.cpjinan.plugin.playerbanex.internal.command.subcommand.Ban
import com.github.cpjinan.plugin.playerbanex.internal.command.subcommand.Kick
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(
    name = "PlayerBanEX",
    aliases = ["playerban","playerban-ex"],
    permissionDefault = PermissionDefault.OP
)
object MainCommand {

    @CommandBody
    val main = mainCommand { createHelper() }
    @CommandBody
    val ban = Ban.ban
    @CommandBody
    val kick = Kick.kick

}