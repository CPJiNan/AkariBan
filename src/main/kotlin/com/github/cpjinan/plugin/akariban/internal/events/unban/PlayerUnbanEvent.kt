package com.github.cpjinan.plugin.akariban.internal.events.unban

import taboolib.platform.type.BukkitProxyEvent

class PlayerUnbanEvent(
    val playerID: String
) :
    BukkitProxyEvent()