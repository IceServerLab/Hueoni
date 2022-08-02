package jp.iceserver.hueoni.config

import hazae41.minecraft.kutils.bukkit.PluginConfigFile

object MainConfig : PluginConfigFile("config")
{
    var prefix by string("prefix")
    var defaultGameTime by int("defaultGameTime")
    var defaultCount by int("defaultCount")
}