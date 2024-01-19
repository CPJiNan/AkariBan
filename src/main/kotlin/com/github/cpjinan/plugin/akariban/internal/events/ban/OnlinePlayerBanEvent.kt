package com.github.cpjinan.plugin.akariban.internal.events.ban

import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class OnlinePlayerBanEvent(
    val player: Player,
    var banReason: String = "",
    var banDuration: String = "",
    var banTime: String = "",
    var banningAdmin: String = ""
) : BukkitProxyEvent()