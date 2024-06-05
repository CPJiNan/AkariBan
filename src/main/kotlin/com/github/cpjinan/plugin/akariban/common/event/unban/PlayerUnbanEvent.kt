package com.github.cpjinan.plugin.akariban.common.event.unban

import taboolib.platform.type.BukkitProxyEvent

class PlayerUnbanEvent(
    val playerID: String
) :
    BukkitProxyEvent()