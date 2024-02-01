package com.github.cpjinan.plugin.akariban.internal.manager

import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToLocalDateTime
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.formatToString
import com.github.cpjinan.plugin.akariban.utils.TimeUtil.parseTime

object TimeManager {
    fun getUnbanTime(banTime: String, banDuration: String): String {
        return if (banTime.isBlank()) ""
        else banTime.formatToLocalDateTime(FormatManager.getTimeFormat())!!
            .plusSeconds(
                banDuration.parseTime(
                    second = ConfigManager.config.getString("options.time-format.duration.second")!!,
                    minute = ConfigManager.config.getString("options.time-format.duration.minute")!!,
                    hour = ConfigManager.config.getString("options.time-format.duration.hour")!!,
                    day = ConfigManager.config.getString("options.time-format.duration.day")!!,
                    week = ConfigManager.config.getString("options.time-format.duration.week")!!,
                    month = ConfigManager.config.getString("options.time-format.duration.month")!!,
                    year = ConfigManager.config.getString("options.time-format.duration.year")!!
                )
            ).formatToString(FormatManager.getTimeFormat())
    }
}