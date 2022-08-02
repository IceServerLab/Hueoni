package jp.iceserver.hueoni.commands

import hazae41.minecraft.kutils.bukkit.msg
import jp.iceserver.hueoni.Hueoni
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ResetCommand : CommandExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        sender as Player

        if (!sender.isOp)
        {
            sender.msg("${MainConfig.prefix} &cあなたは実行する権限が有りません。")
            return true
        }

        Bukkit.getOnlinePlayers().forEach {
            if (Bukkit.getScoreboardManager().mainScoreboard.getTeam("ADMIN")!!.hasEntry(it.name)) return@forEach

            it.inventory.clear()
            it.health = 20.0
        }

        GameManager.getInstance().countdown = MainConfig.defaultGameTime
        Hueoni.plugin.teamManager?.resetTeams()
        Hueoni.plugin.teamManager?.createTeam(
            Pair("NIGE", ChatColor.WHITE),
            Pair("ONI", ChatColor.RED),
            Pair("ADMIN", ChatColor.GOLD)
        )

        sender.playSound(sender.location, Sound.BLOCK_NOTE_BLOCK_PLING, 30f, 1f)
        sender.msg("${MainConfig.prefix} &eゲームをリセットしました。")
        return true
    }
}