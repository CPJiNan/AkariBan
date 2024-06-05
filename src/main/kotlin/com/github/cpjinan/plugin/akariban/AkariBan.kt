package com.github.cpjinan.plugin.akariban

import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

object AkariBan : Plugin() {
    val plugin by lazy { BukkitPlugin.getInstance() }
}