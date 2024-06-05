package com.github.cpjinan.plugin.akariban.common.event.whitelist

import taboolib.platform.type.BukkitProxyEvent

class PlayerWhitelistEvent(
    val playerID: String,
    var isWhitelisted: Boolean = false,
    var whitelistTime: String = "",
    var whitelistingAdmin: String = ""
) :
    BukkitProxyEvent()