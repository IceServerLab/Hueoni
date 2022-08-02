package jp.iceserver.hueoni.listeners

import jp.iceserver.hueoni.Hueoni
import jp.iceserver.hueoni.config.MainConfig
import jp.iceserver.hueoni.game.GameManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.TitlePart
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
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

        if (Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(damager.name)!!.name == "ONI"
                && Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(player.name)!!.name == "NIGE")
        {
            player.health = 20.0
            player.playSound(player.location, Sound.ITEM_ARMOR_EQUIP_DIAMOND, 70f, 1f)
            Hueoni.plugin.teamManager!!.mountOniArmor(player)
            Bukkit.getScoreboardManager().mainScoreboard.getTeam("ONI")!!.addEntry(player.name)
            Bukkit.broadcast(Component.text("${MainConfig.prefix} ${ChatColor.RED}${player.name} ${ChatColor.YELLOW}は ${ChatColor.RED}${damager.name} ${ChatColor.YELLOW}に捕まった。").asComponent())

        }

        if (Bukkit.getScoreboardManager().mainScoreboard.getTeam("NIGE")!!.size != 0) return

        GameManager.getInstance().gameTime = 0
    }
}