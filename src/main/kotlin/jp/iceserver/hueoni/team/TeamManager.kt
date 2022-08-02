package jp.iceserver.hueoni.team

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class TeamManager
{
    private val board: Scoreboard = Bukkit.getScoreboardManager().mainScoreboard

    fun createTeam(vararg pair: Pair<String, ChatColor>): Team
    {
        var team: Team? = null

        pair.forEach {
            team = board.registerNewTeam(it.first)
            team?.suffix = ChatColor.RESET.toString()
            team?.color = it.second
            team?.displayName = it.first
            team?.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS)
            team?.setAllowFriendlyFire(true)
        }

        return team!!
    }

    fun resetTeams() = board.teams.forEach { it.unregister() }
}