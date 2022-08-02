package jp.iceserver.hueoni.listeners

import jp.iceserver.hueoni.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerConnection : Listener
{
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent)
    {
        if (GameManager.getInstance().runnable == null) return

        if (Bukkit.getScoreboardManager().mainScoreboard.getPlayerTeam(e.player) != null) return

        Bukkit.getScoreboardManager().mainScoreboard.getTeam("ONI")!!.addPlayer(e.player)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent)
    {
        if (GameManager.getInstance().runnable == null) return

        val team = Bukkit.getScoreboardManager().mainScoreboard.getPlayerTeam(e.player) ?: return

        if (team.name == "ADMIN") return

        team.removePlayer(e.player)
    }
}