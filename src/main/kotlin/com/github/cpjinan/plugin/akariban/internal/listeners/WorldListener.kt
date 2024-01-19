package com.github.cpjinan.plugin.akariban.internal.listeners

import com.github.cpjinan.plugin.akariban.internal.manager.DatabaseManager
import taboolib.common.platform.event.SubscribeEvent

object WorldListener {
    @SubscribeEvent
    @Suppress("UNUSED_PARAMETER")
    fun onWorldSave(event: org.bukkit.event.world.WorldSaveEvent) {
        DatabaseManager.getDatabase().save()
    }
}