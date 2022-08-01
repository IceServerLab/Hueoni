package jp.iceserver.hueoni.game

import jp.iceserver.hueoni.Hueoni
import jp.iceserver.hueoni.config.MainConfig
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.scheduler.BukkitRunnable
import java.lang.StrictMath.floor

class GameManager
{
    companion object
    {
        private var instance: GameManager? = null

        fun getInstance(): GameManager
        {
            if (instance == null)
                instance = GameManager()

            return instance!!
        }
    }

    var gameTime: Int = MainConfig.defaultGameTime

    fun startGame(world: World)
    {
        Bukkit.getOnlinePlayers().forEach {
            if (listOf(GameMode.CREATIVE, GameMode.SPECTATOR).contains(it.gameMode)) return@forEach

            it.gameMode = GameMode.SURVIVAL
            it.health = 20.0
        }
        world.difficulty = Difficulty.NORMAL

        object: BukkitRunnable()
        {
            var countdown: Int = gameTime

            override fun run()
            {
                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(
                        Component.text(
                        "${ChatColor.YELLOW}${ChatColor.BOLD}ゲーム時間 ${ChatColor.RESET}${convertTime(countdown).first}:${convertTime(countdown).second}"
                    ))
                }
                countdown--
            }
        }.runTaskTimer(Hueoni.plugin, 0L, 20L)
    }

    fun convertTime(time: Int): Pair<Int, Int> = Pair(floor(time / 60.0).toInt(), time - floor(time / 60.0).toInt() * 60)
}