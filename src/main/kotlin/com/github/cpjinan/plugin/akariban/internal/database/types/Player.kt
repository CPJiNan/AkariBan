package com.github.cpjinan.plugin.akariban.internal.database.types

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    var playerID: String = "", // Identifier of the player, typically the player's UUID or username
    var isBanned: Boolean = false, // Flag indicating whether the player is currently banned
    var banReason: String = "", // Reason for the player's ban
    var banDuration: String = "", // Duration of the ban
    var banTime: String = "", // Time when the player was banned
    var unbanTime: String = "", // Time when the ban will be lifted, if there's a limited ban duration
    var banningAdmin: String = "" // Administrator who executed the ban
)
