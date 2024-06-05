package com.github.cpjinan.plugin.akariban.internal.database.type

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    var isBanned: Boolean = false, // Flag indicating whether the player is currently banned
    var banReason: String = "", // Reason for the player's ban
    var banDuration: String = "", // Duration of the ban
    var banTime: String = "", // Time when the player was banned
    var unbanTime: String = "", // Time when the ban will be lifted, if there's a limited ban duration
    var banningAdmin: String = "", // Administrator who executed the ban
    var isWhitelisted: Boolean = false, // Flag indicating whether the player is currently whitelisted
    var whitelistTime: String = "", // Time when the player was whitelisted
    var whitelistingAdmin: String = "" // Administrator who executed the whitelist
)
