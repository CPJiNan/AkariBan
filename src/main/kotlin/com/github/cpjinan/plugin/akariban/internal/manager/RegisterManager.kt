package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akarilib.api.PluginRegistry
import com.github.cpjinan.plugin.akarilib.utils.MetricsUtil
import taboolib.common.platform.function.info
import taboolib.platform.BukkitPlugin
import java.net.HttpURLConnection
import java.net.URL

object RegisterManager {
    fun registerAll() {
        registerUrl()
        registerUpdate()
        DatabaseManager.getDatabase().save()
        PluginRegistry.registerPlugin(BukkitPlugin.getInstance())
        MetricsUtil.registerBukkitMetrics(18992, BukkitPlugin.getInstance())
    }

    private fun registerUrl() {
        Thread {
            val urlConnection =
                URL("https://cpjinan.github.io/Pages/AkariBan/notice.html").openConnection() as HttpURLConnection
            try {
                val message = urlConnection.inputStream.bufferedReader().readText()
                if (message.length > 2) info(message)
            } catch (_: java.net.ConnectException) {
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    private fun registerUpdate() {
        if (ConfigManager.isUpdateEnabled()) {
            Thread {
                val urlConnection =
                    URL("https://cpjinan.github.io/Pages/AkariBan/version.html").openConnection() as HttpURLConnection
                try {
                    val latestVersion = urlConnection.inputStream.bufferedReader().readText()
                    val version = BukkitPlugin.getInstance().description.version
                    if (latestVersion != version) {
                        info("发现了一个新的AkariBan版本！")
                        info("最新版本: $latestVersion")
                        info("当前版本: $version")
                        info("请加QQ群704109949以获取插件最新版本...")
                    }
                } catch (_: java.net.ConnectException) {
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        }
    }
}