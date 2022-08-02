package jp.iceserver.hueoni.listeners

import jp.iceserver.hueoni.Hueoni
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.game.GameManager
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class EntityDamage : Listener
{
    @EventHandler
    fun onEntityDamage(e: EntityDamageEvent)
    {
        if (GameManager.getInstance().runnable == null) return
        if (e.cause != EntityDamageEvent.DamageCause.FALL) return

        e.damage = 0.0
    }

    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent)
    {
        if (GameManager.getInstance().runnable == null) return
        if (e.entity !is Player) return

        val player: Player = e.entity as Player
        val damager: Player = e.damager as Player

        if (Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(player.name)!!.name == "ONI"
                && Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(damager.name)!!.name == "NIGE")
        {
            damager.health = 20.0
            Hueoni.plugin.teamManager!!.mountOniArmor(damager)
            Bukkit.getScoreboardManager().mainScoreboard.getTeam("ONI")!!.addEntry(damager.name)
            Bukkit.broadcast(Component.text("${MainConfig.prefix} &c${damager.name} &eは &c${player.name} &eに捕まった。").asComponent())
        }
    }
}