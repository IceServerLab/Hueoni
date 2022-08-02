package jp.iceserver.hueoni.commands

import hazae41.minecraft.kutils.bukkit.msg
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.game.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class FinishCommand : CommandExecutor
{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        if (!sender.isOp)
        {
            sender.msg("${MainConfig.prefix} &cあなたは実行する権限が有りません。")
            return true
        }

        if (GameManager.getInstance().runnable == null)
        {
            sender.msg("${MainConfig.prefix} &cゲームは開始されていません。")
            return true
        }

        GameManager.getInstance().forceFinishGame()
        sender.msg("${MainConfig.prefix} &4ゲームを強制終了しました。")
        return true
    }
}