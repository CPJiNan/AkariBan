package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akariban.internal.database.Database
import com.github.cpjinan.plugin.akariban.internal.database.DbCbor
import com.github.cpjinan.plugin.akariban.internal.database.DbJson
import com.github.cpjinan.plugin.akariban.internal.database.DbSql

object DatabaseManager {
    private var database: Database? = null

    private fun openDatabase(): Database {
        val dbType = ConfigManager.getMethod() ?: "JSON"
        return when (dbType) {
            "JSON" -> {
                DbJson()
            }

            "CBOR" -> {
                DbCbor()
            }

            "SQL" -> {
                DbSql()
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