package com.github.cpjinan.plugin.playerbanex.internal.module

import com.github.cpjinan.plugin.playerbanex.internal.manager.ConfigManager
import taboolib.common.platform.Platform
import taboolib.common.platform.function.info
import taboolib.module.metrics.Metrics
import taboolib.platform.BukkitPlugin
import java.net.HttpURLConnection
import java.net.URL

object RegisterModule {

    /**
     * 快捷注册方法
     */
    fun registerAll() {
        registerMetrics()
        registerUpdate()
        registerUrl()
    }

    /**
     * bStats统计注册方法
     */
    private fun registerMetrics() {
        if (ConfigManager.options.getBoolean("metrics")) Metrics(18992, BukkitPlugin.getInstance().description.version, Platform.BUKKIT)
    }

    /**
     * 网页读取注册方法
     */
    private fun registerUrl() {
        Thread{
            val urlConnection = URL("https://cpjinan.github.io/Pages/PlayerBanEX/notice.html").openConnection() as HttpURLConnection
            try {
                val message = urlConnection.inputStream.bufferedReader().readText()
                if(message.length > 2) info(message)
            } catch (_: java.net.ConnectException){
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    /**
     * 输出插件更新提示方法
     */
    private fun registerUpdate() {
        if(ConfigManager.options.getBoolean("update")) {
            Thread {
                val urlConnection =
                    URL("https://cpjinan.github.io/Pages/PlayerBanEX/version.html").openConnection() as HttpURLConnection
                try {
                    val latestVersion = urlConnection.inputStream.bufferedReader().readText()
                    val version = BukkitPlugin.getInstance().description.version
                    if (latestVersion != version) {
                        info("发现了一个新的PlayerBanEX版本！")
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