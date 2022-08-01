package jp.iceserver.hueoni

import hazae41.minecraft.kutils.bukkit.init
import jp.iceserver.hueoni.commands.*
import jp.iceserver.hueoni.config.MainConfig

class Hueoni : AbstractHueoni()
{
    companion object
    {
        lateinit var plugin: Hueoni
    }

    override fun onEnable()
    {
        plugin = this

        init(MainConfig)
        MainConfig.autoSave = true

        registerCommands(
            "start" to StartCommand()
        )
    }
}