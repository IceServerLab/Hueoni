package jp.iceserver.hueoni.commands

import hazae41.minecraft.kutils.bukkit.msg
import jp.iceserver.hueoni.Hueoni
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.game.GameManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.TitlePart
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class StartCommand : CommandExecutor
{
    private var runnable: BukkitRunnable? = null

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean
    {
        sender as Player

        if (!sender.isOp)
        {
            sender.msg("${MainConfig.prefix} &cあなたは実行する権限が有りません。")
            return true
        }

        if (runnable != null)
        {
            sender.msg("${MainConfig.prefix} &cゲーム開始カウントは既に始まっています。")
            return true
        }

        if (GameManager.getInstance().runnable != null)
        {
            sender.msg("${MainConfig.prefix} &cゲームは既に始まっています。")
            return true
        }

        runnable = object: BukkitRunnable()
        {
            var count = MainConfig.defaultCount

            override fun run()
            {
                if (count == 0)
                {
                    this.cancel()
                    GameManager.getInstance().startGame(sender.world)
                    runnable = null
                    return
                }

                Bukkit.getOnlinePlayers().forEach {
                    it.sendTitlePart(TitlePart.TITLE, Component.text("${ChatColor.YELLOW}$count").asComponent())
                    it.playSound(it.location, Sound.UI_BUTTON_CLICK, 40f, 1f)

                    if ((Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(it.name) ?: return@forEach).name != "ONI") return@forEach

                    Hueoni.plugin.teamManager!!.mountOniArmor(it)
                }

                count--
            }
        }
        runnable?.runTaskTimer(Hueoni.plugin, 0L, 20L)
        return true
    }
}