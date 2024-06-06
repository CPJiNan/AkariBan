package com.github.cpjinan.plugin.akariban.internal.database

import com.github.cpjinan.plugin.akariban.internal.database.type.Player
import com.github.cpjinan.plugin.akariban.internal.manager.ConfigManager
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table

class DbSql : Database {
    private val host = ConfigManager.getSqlHost()
    private val dataSource by lazy { host.createDataSource() }
    private val table = Table(ConfigManager.getSqlTable(), host) {
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

    override fun getPlayerByName(name: String): Player {
        return table.select(dataSource) {
            where { "playerID" eq name }
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
        if (!table.find(dataSource) { where { "playerID" eq name } }) {
            table.insert(
                dataSource,
                "playerID",
                "isBanned",
                "banReason",
                "banDuration",
                "banTime",
                "unbanTime",
                "banningAdmin"
            ) {
                value(name, false, "", "", "", "", "")
            }
        }
        table.update(dataSource) {
            where { "playerID" eq name }
            set("isBanned", value.isBanned)
            set("banReason", value.banReason)
            set("banDuration", value.banDuration)
            set("banTime", value.banTime)
            set("unbanTime", value.unbanTime)
            set("banningAdmin", value.banningAdmin)
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