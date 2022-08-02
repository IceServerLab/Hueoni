package jp.iceserver.hueoni.listeners

import jp.iceserver.hueoni.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClick : Listener
{
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent)
    {
        if (GameManager.getInstance().runnable == null) return

        val player: Player = e.whoClicked as Player

        if ((Bukkit.getScoreboardManager().mainScoreboard.getEntryTeam(player.name) ?: return).name != "ONI") return

        when (e.currentItem?.type)
        {
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS -> e.isCancelled = true
            else -> {}
        }
    }
}