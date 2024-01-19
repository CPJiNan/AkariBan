package com.github.cpjinan.plugin.akariban.internal.events.ban

import taboolib.platform.type.BukkitProxyEvent

class OfflinePlayerBanEvent(
    val playerName: String,
    var banReason: String = "",
    var banDuration: String = "",
    var banTime: String = "",
    var banningAdmin: String = ""
) : BukkitProxyEvent()