package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akariban.internal.database.Database
import com.github.cpjinan.plugin.akariban.internal.database.DbCbor
import com.github.cpjinan.plugin.akariban.internal.database.DbJson

object DatabaseManager {
    private var database: Database? = null

    private fun openDatabase(): Database {
        val dbType = ConfigManager.config.getString("options.database.type") ?: "JSON"
        val dbUri = ConfigManager.config.getString("options.database.uri")
        return when (dbType) {
            "JSON" -> {
                DbJson(dbUri ?: "database.json")
            }

            "CBOR" -> {
                DbCbor(dbUri ?: "database.cbor")
            }

            else -> {
                throw IllegalArgumentException("unknown dbType")
            }
        }
    }

    fun getDatabase(): Database = if (database != null) {
        database!!
    } else {
        database = openDatabase()
        database!!
    }
}