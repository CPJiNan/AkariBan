package com.github.cpjinan.plugin.akariban.common.event.kick

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class PlayerKickEvent(
    val player: Player,
    var kickReason: String = "",
    var kickTime: String = "",
    var kickingAdmin: String = ""
) :
    BukkitProxyEvent()