package com.github.cpjinan.plugin.akariban.internal.database

import com.github.cpjinan.plugin.akariban.internal.database.types.Player
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.HostSQL
import taboolib.module.database.Table

class DbSql : Database {
    private val host = HostSQL(ConfigManager.config.getConfigurationSection("options.database.sql")!!)
    private val table = Table("akariban", host) {
        add("playerID") {
            type(ColumnTypeSQL.VARCHAR, 255) {
                options(ColumnOptionSQL.PRIMARY_KEY)
            }
        }
        add("isBanned") {
            type(ColumnTypeSQL.BOOLEAN)
        }
        add("banReason") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
        add("banDuration") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
        add("banTime") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
        add("unbanTime") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
        add("banningAdmin") {
            type(ColumnTypeSQL.VARCHAR, 255)
        }
    }

    private val dataSource = host.createDataSource()

    override fun getPlayerByName(name: String): Player {
        return table.select(dataSource) {
            where { "user" eq name }
        }.firstOrNull {
            Player(
                this.getBoolean("isBanned"),
                this.getString("banReason"),
                this.getString("banDuration"),
                this.getString("banTime"),
                this.getString("unbanTime"),
                this.getString("banningAdmin")
            )
        } ?: Player()
    }

    override fun updatePlayer(name: String, value: Player) {
        table.update(dataSource) {
            set("isBanned", value.isBanned)
            set("banReason", value.banReason)
            set("banDuration", value.banDuration)
            set("banTime", value.banTime)
            set("unbanTime", value.unbanTime)
            set("banningAdmin", value.banningAdmin)
            where { "user" eq name }
        }
    }

    override fun save() {}

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    companion object {
        val INSTANCE = DbSql()
    }
}