package jp.iceserver.hueoni

import hazae41.minecraft.kutils.bukkit.init
import jp.iceserver.hueoni.commands.*
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.listeners.*
import jp.iceserver.hueoni.team.TeamManager
import org.bukkit.ChatColor

class Hueoni : AbstractHueoni()
{
    companion object
    {
        lateinit var plugin: Hueoni
    }

    var teamManager: TeamManager? = null

    override fun onEnable()
    {
        plugin = this

        init(MainConfig)
        MainConfig.autoSave = true

        teamManager = TeamManager()
        teamManager?.createTeam(
            Pair("NIGE", ChatColor.WHITE),
            Pair("ONI", ChatColor.RED),
            Pair("ADMIN", ChatColor.GOLD)
        )

        registerListeners(
            EntityDamage(), InventoryClick()
        )

        registerCommands(
            "start" to StartCommand(),
            "finish" to FinishCommand(),
            "reset" to ResetCommand()
        )
    }

    override fun onDisable()
    {
        teamManager?.resetTeams()
    }
}