package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akariban.utils.LoggerUtil.message
import com.github.cpjinan.plugin.akariban.utils.VersionUtil.toSemanticVersion
import taboolib.module.chat.colored
import taboolib.platform.BukkitPlugin
import java.net.HttpURLConnection
import java.net.URL

object UpdateManager {

    fun checkUpdate() {
        getPluginNotice()
        getPluginUpdate()
    }

    private fun getPluginNotice() {
        Thread {
            val urlConnection =
                URL("https://cpjinan.github.io/Pages/AkariBan/notice.html").openConnection() as HttpURLConnection
            try {
                val message = urlConnection.inputStream.bufferedReader().readText()
                if (message.isNotBlank()) message(message.colored())
            } catch (_: java.net.ConnectException) {
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    private fun getPluginUpdate() {
        if (ConfigManager.isUpdateEnabled()) {
            Thread {
                val urlConnection =
                    URL("https://cpjinan.github.io/Pages/AkariBan/version.html").openConnection() as HttpURLConnection
                try {
                    val latestVersion = urlConnection.inputStream.bufferedReader().readText().toSemanticVersion()!!
                    val currentVersion = BukkitPlugin.getInstance().description.version.toSemanticVersion()!!
                    if (latestVersion > currentVersion) {
                        message(
                            "&r===============[&e&lUpdate&r]==============".colored(),
                            "&r| &rPlugin &6AkariBan &7=>".colored(),
                            "&r| &b◈ &r发现了一个新的插件版本！".colored(),
                            "&r| &b◈ &r最新版本: $latestVersion".colored(),
                            "&r| &b◈ &r当前版本: $currentVersion".colored(),
                            "&r| &b◈ &r请加QQ群704109949以获取插件最新版本...".colored(),
                            "&r===============[&e&lUpdate&r]==============".colored()
                        )
                    }
                } catch (_: java.net.ConnectException) {
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        }
    }
}