package jp.iceserver.hueoni.commands

import hazae41.minecraft.kutils.bukkit.msg
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartCommand : CommandExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        sender as Player

        if (!sender.isOp)
        {
            sender.msg("${MainConfig.prefix} &cあなたは実行する権限が有りません。")
            return true
        }

        GameManager.getInstance().startGame(sender.world)
        return true
    }
}