package com.github.cpjinan.manager

import taboolib.common.platform.function.info

object DebugManager {

    /**
     * 输出调试信息方法
     */
    fun debugPrint(message:String?) {
        if (ConfigManager.options.getBoolean("debug")) info(message)
    }

    /**
     * 输出字符Logo方法
     */
    fun logoPrint() {
        info(" ______  _                           ______                _____ __   __  ")
        info(" | ___ \'| |                          | ___ \'              |  ___|\' \' / /  ")
        info(" | |_/ /| |  __ _  _   _   ___  _ __ | |_/ /  __ _  _ __  | |__   \' V /   ")
        info(" |  __/ | | / _` || | | | / _ \'| '__|| ___ \' / _` || '_ \' |  __|  /   \'   ")
        info(" | |    | || (_| || |_| ||  __/| |   | |_/ /| (_| || | | || |___ / /^\' \'  ")
        info(" \'_|    |_| \'__,_| \'__, | \'___||_|   \'____/  \'__,_||_| |_|\'____/ \'/   \'/  ")
        info("                    __/ |                                                 ")
        info("                   |___/                                                  ")
    }

}