package com.github.cpjinan.plugin.akariban.internal.commands

import com.github.cpjinan.plugin.akariban.internal.commands.subCommands.Ban
import com.github.cpjinan.plugin.akariban.internal.commands.subCommands.Kick
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(name = "akariban", permissionDefault = PermissionDefault.OP)
object MainCommand {
    @CommandBody
    val main = mainCommand { createHelper() }

    @CommandBody
    val kick = Kick.kick

    @CommandBody
    val ban = Ban.ban
}