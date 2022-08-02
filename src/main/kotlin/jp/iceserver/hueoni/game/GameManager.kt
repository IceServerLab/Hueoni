package jp.iceserver.hueoni.game

import jp.iceserver.hueoni.Hueoni
import jp.iceserver.hueoni.config.MainConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.title.TitlePart
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

    var countdown: Int = MainConfig.defaultGameTime
    var runnable: BukkitRunnable? = null

    fun startGame(world: World)
    {
        Bukkit.getOnlinePlayers().forEach {
            it.sendTitlePart(TitlePart.TITLE, Component.text("${ChatColor.DARK_RED}${ChatColor.BOLD}ゲーム開始！！").asComponent())
            it.sendTitlePart(TitlePart.SUBTITLE, Component.text("${ChatColor.GRAY}増え鬼").asComponent())
            it.playSound(it.location, Sound.ENTITY_GENERIC_EXPLODE, 40f, 1f)

            if (listOf(GameMode.CREATIVE, GameMode.SPECTATOR).contains(it.gameMode))
            {
                Bukkit.getScoreboardManager().mainScoreboard.getTeam("ADMIN")!!.addEntry(it.name)
                return@forEach
            }

            it.gameMode = GameMode.ADVENTURE
            it.health = 20.0

            if (Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(it.name) != null) return@forEach
            Bukkit.getScoreboardManager().mainScoreboard.getTeam("NIGE")!!.addEntry(it.name)
        }
        world.difficulty = Difficulty.PEACEFUL
        world.setGameRule(GameRule.FALL_DAMAGE, false)

        runnable = object: BukkitRunnable()
        {
            override fun run()
            {
                if (countdown == 0)
                {
                    this.cancel()

                    Bukkit.getOnlinePlayers().forEach {
                        it.sendTitlePart(TitlePart.TITLE, Component.text(
                            if (Bukkit.getScoreboardManager().mainScoreboard.getTeam("NIGE")!!.size == 0)
                                "${ChatColor.RED}${ChatColor.BOLD}鬼側の勝利！！"
                            else "${ChatColor.GREEN}${ChatColor.BOLD}逃げ側の勝利！！"
                        ).asComponent())
                        it.sendTitlePart(TitlePart.SUBTITLE, Component.text("${ChatColor.GRAY}増え鬼").asComponent())
                        it.playSound(it.location, Sound.ENTITY_GENERIC_EXPLODE, 40f, 1f)
                    }
                }

                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(
                        generateInfoBar(
                            "${ChatColor.RED}${ChatColor.BOLD}鬼 ${ChatColor.RESET}${Bukkit.getScoreboardManager().mainScoreboard.getTeam("ONI")!!.size}",
                            "${ChatColor.YELLOW}${ChatColor.BOLD}ゲーム時間 ${ChatColor.RESET}${convertTime(countdown).first}:${if (convertTime(countdown).second < 10) "0${convertTime(countdown).second}" else convertTime(countdown).second}"
                        )
                    )
                }
                countdown--
            }
        }
        runnable?.runTaskTimer(Hueoni.plugin, 0L, 20L)
    }

    fun forceFinishGame()
    {
        if (runnable == null) return

        runnable?.cancel()

        Bukkit.getOnlinePlayers().forEach {
            it.sendTitlePart(TitlePart.TITLE, Component.text("${ChatColor.YELLOW}${ChatColor.BOLD}ゲームを強制終了しました").asComponent())
            it.sendTitlePart(TitlePart.SUBTITLE, Component.text("${ChatColor.GRAY}増え鬼").asComponent())
            it.playSound(it.location, Sound.ENTITY_GENERIC_EXPLODE, 40f, 1f)
        }
    }

    fun convertTime(time: Int): Pair<Int, Int> = Pair(floor(time / 60.0).toInt(), time - floor(time / 60.0).toInt() * 60)

    private fun generateInfoBar(vararg info: String): TextComponent
    {
        val builder: StringBuilder = StringBuilder()

        info.forEach {
            builder.append(it)
            builder.append("${ChatColor.GRAY} | ")
        }

        builder.setLength(builder.length - 5)
        return Component.text(builder.toString())
    }
}